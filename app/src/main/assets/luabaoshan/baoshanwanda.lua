local function DEFAULT_STYLE()
    return {
        ['2d'] = {
            style = 'polygon',
            face = {
                color = '0xB6DFFF',
                enable_alpha = false,
                texture = null,
                automatic_scale = null
            },
            outline = {
                color = '0x97D0FF',
                width = 0.2,
                enable_alpha = false,
            },
            left_side = {}
        },
        ['3d'] = {
            style = 'polygon',
            face_on_bottom = false;
            height = 1;
            face = {
                color = '0xffB6DFFF',
                enable_alpha = false,
                texture = null,
                automatic_scale = null
            },
            outline = {
                color = '0xff97D0FF',
                width = 0.2,
                height = 1,
                left_side = {
                    color = '0xff898989'
                },
                right_side = {
                    color = '0xff898989'
                },
                top_side = {
                    color = '0xff97867d'
                }
            }
        }
    }
end

--a:poi面颜色；b:poi边框颜色；c:poi边框宽；d:3d面是否在下；e:3d面高
local function COLOR_STYLE(a, b, c, d, e, f)
    style = DEFAULT_STYLE()
    -- 2d属性
    if a then style['2d'].face.color = a end;
    if b then style['2d'].outline.color = b end;
    if c then style['2d'].outline.width = c end;
    if f then style['2d'].face.enable_alpha = f end;
    -- 3d属性
    if a then style['3d'].face.color = a end;
    if b then style['3d'].outline.color = b end;
    if c then style['3d'].outline.width = c end;
    if d then style['3d'].face_on_bottom = d end;
    if e then style['3d'].height = e end
    if e then style['3d'].outline.height = e end
    return style
end

local function TEXTURE_1_STYLE(a, b, c)
    style = DEFAULT_STYLE()
    style['2d'].face.color = null;
    style['2d'].face.enable_alpha = true;
    style['2d'].face.texture = a;
    style['2d'].outline.color = b or '0xff7D7D7D';
    style['2d'].face.automatic_scale = true;
    style['2d'].outline.width = c or 0.1;
    -- 3d属性
    style['3d'].face.color = null;
    style['3d'].face.enable_alpha = true;
    style['3d'].face.texture = a;
    style['3d'].outline.color = b or '0xff7D7D7D';
    style['3d'].face.automatic_scale = true;
    style['3d'].outline.width = c or 0.1;
    return style
end

local function TEXTURE_2_STYLE(a, b, c)
    style = DEFAULT_STYLE()
    style['2d'].face.color = null;
    style['2d'].face.enable_alpha = true;
    style['2d'].face.texture = a;
    style['2d'].outline.color = b or '0xff7D7D7D';
    style['2d'].face.automatic_scale = false;
    style['2d'].outline.width = c or 0.1;
    -- 3d属性
    style['3d'].face.color = null;
    style['3d'].face.enable_alpha = true;
    style['3d'].face.texture = a;
    style['3d'].outline.color = b or '0xff7D7D7D';
    style['3d'].face.automatic_scale = false;
    style['3d'].outline.width = c or 0.1;
    return style
end

local function DEFAULT_ICON()
    return {
        ['2d'] = {
            style = 'icon',
            --icon = "icons/00000000.png",
            use_texture_origin_size = false,
            width = 30,
            height = 30,
            anchor_x = 0.5,
            anchor_y = 0.5
        }
    }
end

local function ICON(a)
    style = DEFAULT_ICON()
    style['2d'].icon = a;
    return style
    --  return {
    --    ['2d'] = {
    --      style = 'icon',
    --      icon = a,
    --      use_texture_origin_size = false,
    --      width = 30,
    --      height = 30,
    --      anchor_x = 0.5,
    --      anchor_y = 0.5
    --    }
    --  }
end

--local function defaultStyle(poiNameField)
--    if poiNameField == nil then poiNameField = 'display' end
--    return {
--        layers = {
--            Frame = {
--                height_offset = 0.1,
--                renderer = {
--                    type = 'simple',
--                    ['2d'] = {
--                        style = 'polygon',
--                        face = {
--                            color = '0xffFAFAFA',
--                            enable_alpha = false,
--                        },
--                        outline = {
--                            color = '0xff000000',
--                            width = 0.6,
--                            enable_alpha = true,
--                        },
--                        left_side = {}
--                    },
--                    ['3d'] = {
--                        style = 'polygon',
--                        face_on_bottom = true;
--                        height = 2;
--                        face = {
--                            color = '0xffF9F9F1',
--                            enable_alpha = false
--                        },
--                        outline = {
--                            color = '0xffCCCCCC',
--                            width = 0.1,
--                            height = 0.4,
--                            left_side = {
--                                color = '0xff898989'
--                            },
--                            right_side = {
--                                color = '0xff898989'
--                            },
--                            top_side = {
--                                color = '0xff97867d'
--                            }
--                        }
--                    }
--                }
--            },
--            Area = {
--                height_offset = 0,
--                renderer = {
--                    type = 'unique',
--                    key = {
--                        'id',
--                        'category',
--                    },
--                    default = DEFAULT_STYLE(),
--                    styles = {
--                        --展示区
--                        [23003000] = COLOR_STYLE('0xffb4d8fc', '0xff617687'),
--                        [23006000] = COLOR_STYLE('0xffb4d8fc', '0xff617687'),
--                        --服务设施
--                        [23058000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11001000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11002000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11003000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11004000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11005000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11006000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11007000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11008000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11009000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11010000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11011000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11012000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11013000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11014000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11015000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11016000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11017000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11018000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11019000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11020000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11021000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11022000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11023000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11201000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11202000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11203000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11204000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11205000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11206000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11207000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11208000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11209000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11210000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11211000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11212000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11213000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11214000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11215000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11216000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11217000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11401000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11405001] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11405002] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11405003] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11401001] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11401002] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11401003] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11401004] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11402001] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11401005] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11401006] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11491000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11452000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11452001] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11452002] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11452003] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11452004] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11451000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11454000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11471000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11453000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11473000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11472000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13151000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13111000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13113000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13114000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15001000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15002000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15003000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15003001] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15003002] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15003003] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15009000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15017000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15038000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15039000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13151001] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13151002] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13151003] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13151004] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13151005] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13151006] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13151007] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152001] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152002] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152003] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152004] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152005] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152006] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152007] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152008] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152009] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152010] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152011] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152012] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152013] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152014] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152015] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13152016] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13153000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13154000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [36001000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [15044000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23060000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23004000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [11000000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23018000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23019000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23021000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [24163000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23033000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23034000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23035000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13156000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [13076000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [22033000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [23037000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        [22005000] = COLOR_STYLE('0xffd4cf97', '0xff6b674d'),
--                        --商务设施
--                        [23001000] = COLOR_STYLE('0xff81c5ca', '0xff446666'),
--                        [23002000] = COLOR_STYLE('0xff81c5ca', '0xff446666'),
--                        [23027000] = COLOR_STYLE('0xff81c5ca', '0xff446666'),
--                        [23011000] = COLOR_STYLE('0xff81c5ca', '0xff446666'),
--                        [23054000] = COLOR_STYLE('0xff81c5ca', '0xff446666'),
--                        [23053000] = COLOR_STYLE('0xff81c5ca', '0xff446666'),
--                        [23038000] = COLOR_STYLE('0xff81c5ca', '0xff446666'),
--                        --公共设施
--                        [22051000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [17001000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [17002000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [17003000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [22040000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [23059000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [24113000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [24112000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [24115000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [24114000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [24162000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [23024000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [22052000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [22053000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [22054000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [22055000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [24119000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [17006000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [23036000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [34001000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [24120000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [23040000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        [23025000] = COLOR_STYLE('0xffbfe8a6', '0xff5c6d51'),
--                        --连通设施
--                        [24097000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [24093000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [24091000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [24095000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [24096000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [24092000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [24094000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [24099000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [24098000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [23041000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [23061000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [23043000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [22006000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [33021000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        [33022000] = COLOR_STYLE('0xff81b5ff', '0xff466487'),
--                        --辅助设施
--                        [17004000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        [22001000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        [22002000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        [22003000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        [22004000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        [23051000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        [35003000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        [23999000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        [23049000] = COLOR_STYLE('0xffebedf2', '0xff757575'),
--                        --中空区域
--                        [23062000] = COLOR_STYLE('0xffFFFFFF', '0xffFFFFFF')
--                    }
--                }
--            },
--            Area_text = {
--                collision_detection = true,
--                font_path = "/storage/emulated/0/Nagrand/lua/DroidSansFallback.ttf", -- 字体文件路径
--                renderer = {
--                    type = 'simple',
--                    ['2d'] = {
--                        style = 'annotation',
--                        color = '0xFF696969',
--                        field = poiNameField,
--                        size = 30,
--                        outline_color = '0xFF000000',
--                        outline_width = 0,
--                        anchor_x = 0.5,
--                        anchor_y = 0.5,
--                        aabbox_extend = 20
--                    },
--                }
--            },
--            Facility = {
--                height_offset = -0.2;
--                collision_detection = true,
--                renderer = {
--                    type = 'unique',
--                    key = {
--                        'category'
--                    },
--                    default = DEFAULT_ICON(),
--                    styles = {
--                        [23024000] = ICON('icons/23024000.png'),
--                        [11401000] = ICON('icons/11401000.png'),
--                        [11452000] = ICON('icons/11452000.png'),
--                        [23002000] = ICON('icons/23002000.png'),
--                        [23018000] = ICON('icons/23018000.png'),
--                        [23019000] = ICON('icons/23019000.png'),
--                        [24091000] = ICON('icons/24091000.png'),
--                        [24093000] = ICON('icons/24093000.png'),
--                        [24097000] = ICON('icons/24097000.png')
--                    }
--                }
--            },
--            testWtm = {
--                height_offset = -0.1,
--                renderer = {
--                    type = 'simple',
--                    ['2d'] = {
--                        style = 'icon',
--                        icon = 'icons/11452000.png',
--                        use_texture_origin_size = false,
--                        width = 30,
--                        height = 30,
--                        anchor_x = 0.5,
--                        anchor_y = 0.5
--                    },
--                }
--            },
--            positioning = {
--                height_offset = -0.4,
--                renderer = {
--                    type = 'simple',
--                    ['2d'] = {
--                        style = 'icon',
--                        icon = 'mapping/location.png',
--                        enable_alpha = true,
--                    },
--                }
--            },
--            navigate = {
--                height_offset = -0.3,
--                renderer = {
--                    type = 'simple',
--                    ['2d'] = {
--                        style = 'linestring',
--                        color = '0xFF698AE7',
--                        line_style = 'NONE',
--                        automatic_scale = true,
--                        enable_alpha = true,
--                        width = 0.5,
--                        has_start = true,
--                        has_end = true,
--                        has_arrow = true
--                    },
--                }
--            }
--        }
--    };
--end

local function baoShanWanDaStyle(poiNameField)
    if poiNameField == nil then poiNameField = 'display' end
    return {
        layers = {
            Frame = {
                height_offset = 0.1,
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'polygon',
                        face = {
--                            color = '0xffFAFAFA',--路
                            color = '0xfffffff8',
                            enable_alpha = false,
                        },
                        outline = {
                            color = '0xffaf967e',--外部边框
                            width = 3,
                            enable_alpha = true,
                        },
                        left_side = {}
                    },
                    ['3d'] = {
                        style = 'polygon',
                        face_on_bottom = true;
                        height = 2;
                        face = {
                            color = '0xffF9F9F1',
                            enable_alpha = false
                        },
                        outline = {
                            color = '0xffCCCCCC',
                            width = 0.1,
                            height = 0.4,
                            left_side = {
                                color = '0xff898989'
                            },
                            right_side = {
                                color = '0xff898989'
                            },
                            top_side = {
                                color = '0xff97867d'
                            }
                        }
                    }
                }
            },
            Area = {
                height_offset = 0,
                renderer = {
                    type = 'unique',
                    key = {
                        'id',
                        'category',
                    },
                    default = DEFAULT_STYLE(),
                    styles = {
                        --快餐

                        [11401001] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11401002] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11401003] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11401004] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11401005] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11401006] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11402001] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11403000] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11404000] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11405001] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11405002] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11405003] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11401000] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11405000] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [11402000] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),
                        [13129000] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),

                        --运动健身
                        ['14\\d{6}'] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),

                        --菜
                        ['11\\d{6}'] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),

                        --生活服务
                        ['15\\d{6}'] = COLOR_STYLE('0xfffcf2e2', '0xfff7d4a3'),

                        --服饰鞋帽皮具


                        [13008000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13001000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13011000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),

                        [13007000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13002000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13003000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13004000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13005000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13006000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13008001] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13008002] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13008003] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13008004] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13008005] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13009000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),


                        [13010000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13031000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13032000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13033000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13034000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13035000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13036000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),

                        --个人护理
                        [13062000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13061000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [13063000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),

                        --超市
                        ['131520\\d{2}'] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),

                        [13153000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13154000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13151007] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13151000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13121000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),

                        [13151001] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13151002] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13151003] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13151004] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13151005] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [13151006] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),


                        [23004000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),

                        --儿童母婴综合
                        ['1305\\d{4}'] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        --工艺品
                        [13073000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        --数码电子
                        [13096000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        --家居用品
                        [13105000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        --文化产品
                        [13117000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),




                        ['1307\\d{4}'] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        ['1311\\d{4}'] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        ['1310\\d{4}'] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        ['1309\\d{4}'] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        ['1200\\d{4}'] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),
                        [15003000] = COLOR_STYLE('0xfff1e8f6', '0xffd1b5e2'),

                        --空铺
                        [23006000] = COLOR_STYLE('0xffebebeb', '0xffbababa'),
                        [23999000] = COLOR_STYLE('0xfff0f0f0', '0xffd0d0d0'),
                        --辅助
                        [23999000] = COLOR_STYLE('0xffebebeb', '0xffbababa'),
                        --停车场
                        [17004000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        [22001000] = COLOR_STYLE('0xfffbebee', '0xfff3bdc8'),
                        --中空
                        [23062000] = COLOR_STYLE('0xffffffff', '0xfff0f0f0'),


                        --哺乳室
                        [23009000] = COLOR_STYLE('0xffe6edf6', '0xffb2c8e4'),

                        --问询处
                        [23019000] = COLOR_STYLE('0xffe6edf6', '0xffb2c8e4'),

                        --洗手间
                        [23024000] = COLOR_STYLE('0xffe6edf6', '0xffb2c8e4'),
                        [23025000] = COLOR_STYLE('0xffe6edf6', '0xffb2c8e4'),
                        [23059000] = COLOR_STYLE('0xffe6edf6', '0xffb2c8e4'),
                        --楼梯
                        [24097000] = COLOR_STYLE('0xffe6f2e7', '0xffb5d9b8'),
                        [24098000] = COLOR_STYLE('0xffe6f2e7', '0xffb5d9b8'),
                        [24093000] = COLOR_STYLE('0xffe6f2e7', '0xffb5d9b8'),
                        [24094000] = COLOR_STYLE('0xffe6f2e7', '0xffb5d9b8'),
                        [24095000] = COLOR_STYLE('0xffe6f2e7', '0xffb5d9b8'),
                        [24096000] = COLOR_STYLE('0xffe6f2e7', '0xffb5d9b8'),
                        [24091000] = COLOR_STYLE('0xffe6f2e7', '0xffb5d9b8'),
                        [24092000] = COLOR_STYLE('0xffe6f2e7', '0xffb5d9b8'),
                    }
                }
            },
            Area_text = {
                collision_detection = true,
                font_path = "/storage/emulated/0/Nagrand/lua/DroidSansFallback.ttf", -- 字体文件路径
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'annotation',
                        color = '0xFF343434',
                        field = poiNameField,
                        size = 25,
                        outline_color = '0xFF000000',
                        outline_width = 0,
                        anchor_x = 0.5,
                        anchor_y = 0.5,
                        aabbox_extend = 20
                    },
                }
            },
            Facility = {
                height_offset = -0.2;
                collision_detection = true,
                renderer = {
                    type = 'unique',
                    key = {
                        'category'
                    },
                    default = DEFAULT_ICON(),
                    styles = {
                        [23024000] = ICON('icons/23024000.png'),
                        [11401000] = ICON('icons/11401000.png'),
                        [11452000] = ICON('icons/11452000.png'),
                        [23002000] = ICON('icons/23002000.png'),
                        [23018000] = ICON('icons/23018000.png'),
                        [23019000] = ICON('icons/23019000.png'),--问讯处
                        [24091000] = ICON('icons/24091000.png'),--电梯
                        [24093000] = ICON('icons/24093000.png'),--扶梯
                        [24097000] = ICON('icons/24097000.png'),  --楼梯
                        [23025000] = ICON('icons/23025000.png'),--女洗手间
                        [23024000] = ICON('icons/23024000.png'),--男洗手间
                        [24112000] = ICON('icons/24112000.png'),--ATM
                        [23041000] = ICON('icons/23041000.png'),--出入口
                        [23009000] = ICON('icons/23009000.png'),--哺乳室
                        [23043000] = ICON('icons/23043000.png'),--建筑物正门
                        [23060000] = ICON('icons/23060000.png'),--收银台
                        [24116000] = ICON('icons/24116000.png'),--生活服务机

                    }
                }
            },
            testWtm = {
                height_offset = -0.1,
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'icon',
                        icon = 'icons/11452000.png',
                        use_texture_origin_size = false,
                        width = 30,
                        height = 30,
                        anchor_x = 0.5,
                        anchor_y = 0.5
                    },
                }
            },
            positioning = {
                height_offset = -0.4,
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'icon',
                        icon = 'mapping/location.png',
                        enable_alpha = true,
                    },
                }
            },
            navigate = {
                height_offset = -0.3,
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'linestring',
                        color = '0xFF0E89DC',
                        line_style = 'NONE',
                        automatic_scale = true,
                        enable_alpha = true,
                        width = 0.5,
                        has_start = true,
                        has_end = true,
                        has_arrow = true
                    },
                }
            }
        }
    };
end


CONFIG = {
    views = {
        default = baoShanWanDaStyle('display');
        defaultEn = baoShanWanDaStyle('englishName');
    }
}
