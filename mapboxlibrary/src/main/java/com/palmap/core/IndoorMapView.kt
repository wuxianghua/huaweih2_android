package com.palmap.core

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.style.functions.Function
import com.mapbox.mapboxsdk.style.functions.stops.IdentityStops
import com.mapbox.mapboxsdk.style.layers.*
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.style.sources.Source
import com.mapbox.services.commons.geojson.Feature
import com.mapbox.services.commons.geojson.FeatureCollection
import com.palmap.core.data.PlanarGraph
import com.palmap.core.style.FeatureRendererImpl
import com.palmap.core.style.renderable.Renderable
import com.palmap.nagrand.support.task.Task
import com.palmap.nagrand.support.task.TaskManager
import java.lang.ref.WeakReference

/**
 * Created by wtm on 2017/8/30.
 */
class IndoorMapView private constructor(
        private val context: Context,
        val name: String) {

    companion object {

        fun create(context: Context, name: String): IndoorMapView {
            return IndoorMapView(context, name)
        }

        fun create(context: Context): IndoorMapView {
            return create(context, "default")
        }

        private val TAG = "IndoorMapView"

        private val styleUrl = "mapbox://styles/mapbox/outdoors-v10"

        private class LoadMapTask(indoorMapView: IndoorMapView,
                                  private val planarGraph: PlanarGraph
        ) : Task<Unit>() {

            private val mActivity: WeakReference<IndoorMapView> = WeakReference(indoorMapView)

            override fun doInBackground() {
                if (mActivity.get() == null) {
                    return
                }
                planarGraph.loadStyle(mActivity.get()!!.renderable!!.renderer)
            }

            override fun onSuccess(t: Unit) {
                var indoorMapView: IndoorMapView? = null
                if (mActivity.get() != null) {
                    indoorMapView = mActivity.get()
                }
                if (indoorMapView == null) {
                    return
                }
                planarGraph.dataMap.forEach { entry ->
                    if (mActivity.get() == null) {
                        return@forEach
                    }
                    val layerName = entry.key

                    val featureCollection = entry.value

                    val render = indoorMapView!!.renderable!!.renderer[layerName] ?: return@forEach
                    val sourceId = layerName + indoorMapView!!.floorId

                    //add source
                    indoorMapView!!.addSource(sourceId, featureCollection)
                    //添加主layer
                    val layer = render.createLayer(layerName, sourceId)
                    indoorMapView!!.addLayer(layer)

                    //是否添加outLine
                    if (render.isHaveOutLineLayer()) {
                        val outLineLayer = LineLayer(layerName + "_outLine", sourceId)
                                .withProperties(
                                        PropertyFactory.lineColor(Function.property("lineColor", IdentityStops<String>())),
                                        PropertyFactory.lineOpacity(Function.property("lineOpacity", IdentityStops<Float>()))
                                )
                        indoorMapView!!.addLayer(outLineLayer)
                    }
                    fun loadIconFromUrl(layerName: String, name: String? = null) {
                        featureCollection.features.forEach { f2 ->
                            if (f2.properties["logo"] != null && (name == null || f2.properties[name] != null)) {
                                indoorMapView!!.loadIcon(f2.properties["logo"].asString, layerName)
                            }
                        }
                    }
                    // 是否添加 Text
                    val layerTextName = layerName + "_Text"
                    if (indoorMapView!!.renderable!!.renderer[layerTextName] != null) {
                        val textRenderer = indoorMapView!!.renderable!!.renderer[layerTextName]!!
                        val textLayer = textRenderer.createLayer(layerTextName, sourceId)
                        val textField = textRenderer.getStyle(FeatureRendererImpl.textField)
                        if (textLayer is SymbolLayer && textField != null) {
                            textLayer.setFilter(Filter.has(textField))
                        }
                        indoorMapView!!.addLayer(textLayer)
                        loadIconFromUrl(layerTextName, textField)
                    }
                    if (layer is SymbolLayer) {
                        loadIconFromUrl(layerName)
                    }
                }
                indoorMapView.canLoadMap = true
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                mActivity.get()?.canLoadMap = true
            }
        }
    }

    private var mapView: MapView = MapView(context, MapboxMapOptions().styleUrl(styleUrl))
    private lateinit var mapBoxMap: MapboxMap
    private var mapReady = false
    private val taskManager: TaskManager = TaskManager()
    private var canLoadMap: Boolean = true
    var floorId: Long = 0

    private var renderable: Renderable? = null

    init {
        renderable = MapEngine.getRenderableByName(this.name)
    }

    //////////////////////////////////////API start///////////////////////////////////////////////////

    /**
     * 添加地图
     */
    fun attachMap(rootLayout: ViewGroup, savedInstanceState: Bundle?, callBack: () -> Unit) {
        mapView.findViewById<View>(com.mapbox.mapboxsdk.R.id.logoView).visibility = View.GONE
        mapView.findViewById<View>(com.mapbox.mapboxsdk.R.id.attributionView).visibility = View.GONE

        rootLayout.addView(mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
            mapBoxMap = mapboxMap
            initMapView()
            mapReady = true
            callBack()
        }
    }

    /**
     * 渲染地图
     */
    fun drawPlanarGraph(planarGraph: PlanarGraph) {
        if (!mapReady) {
            throw IllegalArgumentException("the map is not ready !!!")
        }
        if (this.renderable == null) {
            throw IllegalArgumentException("the mapStyle is error !!!" + MapEngine.stylePath)
        }
        if (!canLoadMap) {
            Log.w(TAG, "current can't load Map! an mapData loading ...")
            return
        }
        fun realLoad() {
            if (!planarGraph.dataCorrect && planarGraph.floorId != 0L) {
                Log.w(TAG, "planarGraph dataCorrect !!!")
                return
            }
            resetNorth()
            removeAllLayer()
            floorId = planarGraph.floorId
            mapBoxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    planarGraph.mapCenter,
                    planarGraph.zoomLevel
                    //15.0
            ))
            taskManager.execTask(LoadMapTask(this@IndoorMapView, planarGraph))
        }
        canLoadMap = false
        if (!planarGraph.isResolve()) { //如果没有解析过数据 解析一下
            taskManager.execTask(object : Task<Unit>() {
                override fun doInBackground() {
                    planarGraph.resolveData()
                }

                override fun onSuccess(t: Unit) {
                    realLoad()
                }

                override fun onError(throwable: Throwable) {
                    super.onError(throwable)
                    Log.w(TAG, "planarGraph dataCorrect !!!")
                    canLoadMap = true
                }
            })
        } else {
            realLoad()
        }
    }

    /**
     * 重置指南
     */
    fun resetNorth(duration: Long = 0) {
        if (!mapReady) {
            return
        }
        mapBoxMap.setFocalBearing(0.0, mapBoxMap.width / 2, mapBoxMap.height / 2, duration)
    }

    fun setOnSingTapListener(onSingTapListener: (lat: Double, lng: Double) -> Unit) {
        this.mapBoxMap.setOnMapClickListener { latLng ->
            onSingTapListener(latLng.latitude, latLng.longitude)
        }
    }

    fun selectFeature(lat: Double, lng: Double): Feature? {
        val listFeature = this.mapBoxMap.queryRenderedFeatures(mapBoxMap.projection.toScreenLocation(LatLng(lat, lng)), "Area")
        if (listFeature.size == 0) {
            return null
        } else {
            return listFeature[0]
        }
    }

    //////////////////////////////////////API end///////////////////////////////////////////////////

    //////////////////////////////////////private start///////////////////////////////////////////////////

    private fun initMapView() {
        mapBoxMap.setMinZoomPreference(15.0)
        mapBoxMap.setMaxZoomPreference(20.0)
        mapBoxMap.layers.forEach { layer ->
            mapBoxMap.removeLayer(layer)
        }
        val backGroundLayer = BackgroundLayer("background")
                .withProperties(
                        PropertyFactory.backgroundColor(if (renderable == null) "#fff" else renderable!!.backgroundColor),
                        PropertyFactory.backgroundOpacity(if (renderable == null) 1.0f else renderable!!.backgroundOpacity)
                )
        mapBoxMap.addLayer(backGroundLayer)
    }

    /**
     * 移除所有图层
     */
    private fun removeAllLayer() {
        if (!mapReady) {
            return
        }
        mapBoxMap.layers.forEach { layer ->
            if ("background" != layer.id) {
                mapBoxMap.removeLayer(layer)
                //mapBoxMap.removeSource(layer.id)
            }
        }
    }

    private fun loadIcon(url: String, layerName: String) {
        Glide.with(context)
                .load("https://api.ipalmap.com/logo/64/" + url)
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        mapBoxMap.addImage(url, resource!!)
                        mapBoxMap.getLayer(layerName)?.setProperties(
                                PropertyFactory.iconImage(Function.property("logo", IdentityStops<String>()))
                        )
                    }
                })
    }

    private fun addSource(sourceId: String, featureCollection: FeatureCollection) {
        if (mapBoxMap.getSourceAs<Source>(sourceId) === null) {
            mapBoxMap.addSource(GeoJsonSource(sourceId, featureCollection))
        }
    }

    private fun addLayer(layer: Layer) {
        mapBoxMap.addLayer(layer)
    }

    //////////////////////////////////////private end///////////////////////////////////////////////////

    //////////////////////////////////////生命周期 start///////////////////////////////////////////////////
    fun onStart() {
        mapView.onStart()
    }

    fun onStop() {
        mapView.onStop()
    }

    fun onResume() {
        mapView.onResume()
    }

    fun onPause() {
        mapView.onPause()
    }

    fun onSaveInstanceState(outState: Bundle?) {
        if (outState != null) {
            mapView.onSaveInstanceState(outState)
        }
    }

    fun onDestroy() {
        mapView.onDestroy()
    }

    fun onLowMemory() {
        mapView.onLowMemory()
    }
    //////////////////////////////////////生命周期 end///////////////////////////////////////////////////
}