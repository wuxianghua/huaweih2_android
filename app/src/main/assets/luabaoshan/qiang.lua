--
-- Created by IntelliJ IDEA.
-- User: eric3
-- Date: 2016/8/31
-- Time: 14:38
-- To change this template use File | Settings | File Templates.
--
local function Set3dColorWith(color, widthColor,topcolor,sidecolor,temph)
    if (temph == 0)
    then
        temph = 10.0
    end
    return {
        ['2d'] = {
            style = 'polygon',
            face = {
                enable_alpha = false,
                color = color,
            },
            outline = {
                color = widthColor,--内部边框
                width = 0.05,
                enable_alpha = false,
            },
            left_side = {}
        },
        ['3d'] = {
            style = 'polygon',
            face_on_bottom = false, --为false时 height才有效
            height = temph, --如果多边形有面的话，要和outline的高度相同
            face = {
                color = topcolor,
                enable_alpha = false
            },
            outline = {
                color = widthColor,
                width = 0.2,
                height = temph,
                enable_alpha=false,
                --   enable_edge_shadow = false,
                left_side = {
                    color = sidecolor,
                    enable_alpha = false
                },

                right_side = {
                    color = sidecolor,
                    enable_alpha = false
                },
                top_side = {
                    color = sidecolor,
                    enable_alpha = false
                }

            }
        }
    }
end

local function Set3dexhibitionColorWith(color, widthColor,topcolor,sidecolor,temph)
    if (temph == 0)
    then
        temph = 10.0
    end
    return {
        ['2d'] = {
            style = 'polygon',
            face = {
                enable_alpha = false,
                color = color,
            },
            outline = {
                color = widthColor,--内部边框
                width = 0.2,
                enable_alpha = false,
            },
            left_side = {}
        },
        ['3d'] = {
            style = 'polygon',
            face_on_bottom = false, --为false时 height才有效
            height = temph, --如果多边形有面的话，要和outline的高度相同
            face = {
                color = topcolor,
                enable_alpha = false
            },
            outline = {
                color = widthColor,
                width = 0.2,
                height = temph,
                enable_alpha=false,
                --   enable_edge_shadow = false,
                left_side = {
                    color = sidecolor,
                    enable_alpha = false
                },

                right_side = {
                    color = sidecolor,
                    enable_alpha = false
                },
                top_side = {
                    color = sidecolor,
                    enable_alpha = false
                }

            }
        }
    }
end

--[[
    ['2d'] = {
    style = 'polygon',
    face = {
    enable_alpha = false,
    color = color,
    },
    outline = {
    color = widthColor,--内部边框
    width = 0.2,
    enable_alpha = false,
    },
    ]]--

local function Set2dColorWith(color, widthColor,leftcolor,rightcolor,height)
    return {
        ['2d'] = {
            style = 'polygon',
            face = {
                enable_alpha = false,
                color = color,
            },
            outline = {
                color = widthColor,
                width = 0.05,
                enable_alpha = false,
            },
            left_side = {}
        },
    }
end


local function SetImageWith( imageName,needTiled ,angle, needAligment)
    -- body
    return{
        ['2d'] = {
            style = 'polygon',
            face = {

                enable_alpha = false,
                texture = imageName,
                automatic_scale = false,
                texture_rotation = angle,
                edge_aligment =  needAligment,
            },
            outline = {
                color = '0xffababab',
                width = 0.03,
                enable_alpha = false,
            },
            left_side = {}
        }


    }
end

local function DEFAULT_STYLE()
    return {
        ['2d'] = {
            style = 'polygon',
            face = {
                color = '0xEEE3DF',--默认颜色
                enable_alpha = false,
                texture = null,
                automatic_scale = null
            },
            outline = {
                --color = '0x97D0FF',
                --color = '0x7F7B79',
                width = 0.25,
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

local function COLOR_STYLE(a, b, c, d, e, f)
    style = DEFAULT_STYLE()
    -- 2d属性
    if a then style['2d'].face.color = a  end;
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


local function DEFAULT_3D_STYLE()
    return{
        ['2d'] = {
            style = 'polygon',
            face = {
                color = '0xffe1e9ef',
                enable_alpha = false,
                texture = null,
                automatic_scale = null
            },
            outline = {
                color = '0xffc0c0c0',
                width = 0.02,
                enable_alpha = false,
            },
            left_side = {}
        },
        ['3d'] = {
            style = 'polygon',
            face_on_bottom = false, --为false时 height才有效
            height = 2, --如果多边形有面的话，要和outline的高度相同
            face = {
                color = '0Xfffff5ee',
                --color = '0xffe1e9ef',
                enable_alpha = false
            },
            outline = {
                color = '0XFF000000',
                width = 0.05,
                height = 2,
                enable_alpha=false,
                left_side = {
                    color = '0XFFeed3c1',
                    enable_alpha = false
                },
                right_side = {
                    color = '0XFFeed3c1',
                    enable_alpha = false
                },
                top_side = {
                    color = '0XFF000000',
                    enable_alpha = false
                }
            }
        }
    }
end

--[[
    local function GET_FONT_PATH()

    local engine = GetEngine()
    local properties = engine.properties
    local os = properties["OS"]
    local os_version = properties["OS_Version"]

    if os == "iPhone OS" then

    if os_version >= "7.0" and os_version < "8.0" then
    return "/System/Library/Fonts/Cache/STHeiti-Light.ttc"
    elseif os_version >= "8.0" and os_version < "9.0" then
    return "/System/Library/Fonts/Core/STHeiti-Light.ttc"
    elseif os_version >= "9.0" then
    return "/System/Library/Fonts/LanguageSupport/PingFang.ttc"
    else
    return "/System/Library/Fonts/LanguageSupport/PingFang.ttc"
    end
    elseif os == "Android" then

    else
    return ""

    end

    end
    --]]

local function GET_YUAN_FONT_PATH()

    --return "xiongtu.ttf"
    return "1.ttf"
end

local function GET_FONT_PATH()

    local engine = GetEngine()
    local properties = engine.properties
    local os = properties["OS"]
    local os_version = properties["OS_Version"]

    if os == "iPhone OS" then

        if os_version >= "7.0" and os_version < "8.0" then
            return "/System/Library/Fonts/Cache/STHeiti-Light.ttc"
        elseif os_version >= "8.0" and os_version < "9.0" then
            return "/System/Library/Fonts/Core/STHeiti-Light.ttc"
        elseif os_version >= "9.0" then
            return "/System/Library/Fonts/LanguageSupport/PingFang.ttc"
        else
            return "/System/Library/Fonts/LanguageSupport/PingFang.ttc"
        end
    elseif os == "Android" then

    else
        return ""

    end

end

local function DEFAULT_3D_STYLE(a,height)
    return{
        ['2d'] = {
            style = 'polygon',
            face = {
                color = '0xffe1e9ef',
                enable_alpha = false,
                texture = null,
                automatic_scale = null
            },
            outline = {
                color = '0xffc0c0c0',
                width = 0.02,
                enable_alpha = false,
            },
            left_side = {}
        },
        ['3d'] = {
            style = 'polygon',
            face_on_bottom = false, --为false时 height才有效
            height = d, --如果多边形有面的话，要和outline的高度相同
            face = {
                --color = '0xffe1e9ef',
                color = '0Xfffff5ee',
                enable_alpha = false
            },
            outline = {
                color = '0XFF000000',
                width = 0.05,
                height = height,
                enable_alpha=false,
                left_side = {
                    color = '0XFFeed3c1',
                    --color = '0xffe1e9ef',

                    enable_alpha = false
                },
                right_side = {
                    color = '0XFFeed3c1',
                    --color = '0xffe1e9ef',
                    enable_alpha = false
                },
                top_side = {
                    color = '0XFFffffff',
                    enable_alpha = false
                }
            }
        }
    }
end

local function COLOR_3D_STYLE(a,b,c,height)
    style = DEFAULT_3D_STYLE(a,height)
    --style = DEFAULT_3D_STYLE()
    style['2d'].face.color = a;
    style['2d'].face.enable_alpha = false;
    style['2d'].outline.color = b or '0xFFc0c0c0';
    style['2d'].outline.width = c or 0.02;
    style['2d'].outline.enable_alpha = false;
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
    return style
end

local function DEFAULT_ICON()
    return {
        ['2d'] = {
            style = 'icon',
            icon = "icons/00000000.png",
            use_texture_origin_size = false,
            width = 45,
            height = 45,
            anchor_x = 0.5,
            anchor_y = 0.5
        }
    }
end

local function ICON(a,length)
    local templ
    if  length then templ = 32 else templ = 28 end
    return {
        ['2d'] = {
            style = 'icon',
            icon = a,
            use_texture_origin_size = false,
            width = 30,
            height = 30,
            anchor_x = 0.5,
            anchor_y = 0.5
        }
    }
end

local function viewStyle(poiNameField)
    if poiNameField == nil then poiNameField = 'display' end

    return
    {
        layers = {
            Frame = {
                height_offset = 0.1,
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'polygon',
                        face = {
                            --color = '0xfffffef4',--路
                            color = '0xfffffff8',
                            enable_alpha = false,
                        },
                        outline = {
                            --color = '0xff000000',
                            color = '0xffaf967e',--外部边框
                            width = 3,
                            enable_alpha = false,
                        },
                        left_side = {}
                    },
                }
            },
            Area = {
                height_offset = 0,
                renderer = {
                    type = 'unique',
                    key = {
                        'name',
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
                font_path = GET_FONT_PATH(),
                --font_path = GET_YUAN_FONT_PATH(),
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'annotation',
                        color = '0xFF343434',
                        --color = '0xFF000000',
                        aabbox_extend = 20,
                        width = 50,
                        field = poiNameField,
                        size = 20,
                        outline_color = '0xFF000000',
                        outline_width = 0,
                        anchor_x = 0.5,
                        anchor_y = 0.5

                    },
                }
            },
            Facility = {
                collision_detection=true;
                height_offset = -0.2;
                renderer = {
                    type = 'unique',
                    key = {
                        'category'
                    },
                    default = DEFAULT_ICON(),
                    styles = {
                        [11000000] = ICON('icons/11000000.png'),
                        [11401000] = ICON('icons/11401000.png'),
                        [11454000] = ICON('icons/11454000.png'),
                        [13076000] = ICON('icons/13076000.png'),
                        [13113000] = ICON('icons/13113000.png'),
                        [13116000] = ICON('icons/13116000.png'),
                        [15001000] = ICON('icons/15001000.png'),
                        [15002000] = ICON('icons/15002000.png'),
                        [15026000] = ICON('icons/15026000.png'),
                        [15043000] = ICON('icons/15043000.png'),
                        [15044000] = ICON('icons/15044000.png'),
                        [17001000] = ICON('icons/17001000.png'),
                        [17004000] = ICON('icons/17004000.png'),
                        [17006000] = ICON('icons/17006000.png'),
                        [17007000] = ICON('icons/17007000.png'),
                        [17008000] = ICON('icons/17008000.png'),
                        [21048000] = ICON('icons/21048000.png'),
                        [21049000] = ICON('icons/21049000.png'),
                        [22011000] = ICON('icons/22011000.png'),
                        [22012000] = ICON('icons/22012000.png'),
                        [22014000] = ICON('icons/22014000.png'),
                        [22015000] = ICON('icons/22015000.png'),
                        [22016000] = ICON('icons/22016000.png'),
                        [22017000] = ICON('icons/22017000.png'),
                        [22019000] = ICON('icons/22019000.png'),
                        [22021000] = ICON('icons/22021000.png'),
                        [22022000] = ICON('icons/22022000.png'),
                        [22023000] = ICON('icons/22023000.png'),
                        [22033000] = ICON('icons/22033000.png'),
                        [22039000] = ICON('icons/22039000.png'),
                        [22040000] = ICON('icons/22040000.png'),
                        [22052000] = ICON('icons/22052000.png'),
                        [22053000] = ICON('icons/22053000.png'),
                        [22054000] = ICON('icons/22054000.png'),
                        [22055000] = ICON('icons/22055000.png'),
                        [23004000] = ICON('icons/23004000.png'),
                        [23005000] = ICON('icons/23005000.png'),
                        [23007000] = ICON('icons/23007000.png'),
                        [23008000] = ICON('icons/23008000.png'),
                        [23009000] = ICON('icons/23009000.png'),
                        [23010000] = ICON('icons/23010000.png'),
                        [23011000] = ICON('icons/23011000.png'),
                        [23012000] = ICON('icons/23012000.png'),
                        [23013000] = ICON('icons/23013000.png'),
                        [23014000] = ICON('icons/23014000.png'),
                        [23015000] = ICON('icons/23015000.png'),
                        [23016000] = ICON('icons/23016000.png'),
                        [23017000] = ICON('icons/23017000.png'),
                        [23018000] = ICON('icons/23018000.png'),
                        [23019000] = ICON('icons/23019000.png'),
                        [23020000] = ICON('icons/23020000.png'),
                        [23021000] = ICON('icons/23021000.png'),
                        [23022000] = ICON('icons/23022000.png'),
                        [23023000] = ICON('icons/23023000.png'),
                        [23025000] = ICON('icons/23025000.png'),
                        [23026000] = ICON('icons/23026000.png'),
                        [23027000] = ICON('icons/23027000.png'),
                        [23028000] = ICON('icons/23028000.png'),
                        [23029000] = ICON('icons/23029000.png'),
                        [23030000] = ICON('icons/23030000.png'),
                        [23031000] = ICON('icons/23031000.png'),
                        [23032000] = ICON('icons/23032000.png'),
                        [23033000] = ICON('icons/23033000.png'),
                        [23034000] = ICON('icons/23034000.png'),
                        [23035000] = ICON('icons/23035000.png'),
                        [23036000] = ICON('icons/23036000.png'),
                        [23037000] = ICON('icons/23037000.png'),
                        [23038000] = ICON('icons/23038000.png'),
                        [23039000] = ICON('icons/23039000.png'),
                        [23040000] = ICON('icons/23040000.png'),
                        [23041000] = ICON('icons/23041000.png'),
                        [23042000] = ICON('icons/23042000.png'),
                        [23059000] = ICON('icons/23059000.png'),
                        [23060000] = ICON('icons/23060000.png'),
                        [23061000] = ICON('icons/23061000.png'),
                        [24003000] = ICON('icons/24003000.png'),
                        [24006000] = ICON('icons/24006000.png'),
                        [24014000] = ICON('icons/24014000.png'),
                        [24091000] = ICON('icons/24091000.png'),
                        [24092000] = ICON('icons/24092000.png'),
                        [24093000] = ICON('icons/24093000.png'),
                        [24094000] = ICON('icons/24094000.png'),
                        [24097000] = ICON('icons/24097000.png'),
                        [24098000] = ICON('icons/24098000.png'),
                        [24099000] = ICON('icons/24099000.png'),
                        [24100000] = ICON('icons/24100000.png'),
                        [24111000] = ICON('icons/24111000.png'),
                        [24112000] = ICON('icons/24112000.png'),
                        [24113000] = ICON('icons/24113000.png'),
                        [24114000] = ICON('icons/24114000.png'),
                        [24115000] = ICON('icons/24115000.png'),
                        [24116000] = ICON('icons/24116000.png'),
                        [24117000] = ICON('icons/24117000.png'),
                        [24118000] = ICON('icons/24118000.png'),
                        [24119000] = ICON('icons/24119000.png'),
                        [24120000] = ICON('icons/24120000.png'),
                        [24121000] = ICON('icons/24121000.png'),
                        [24141000] = ICON('icons/24141000.png'),
                        [24142000] = ICON('icons/24142000.png'),
                        [24151000] = ICON('icons/24151000.png'),
                        [24152000] = ICON('icons/24152000.png'),
                        [24161000] = ICON('icons/24161000.png'),
                        [24162000] = ICON('icons/24162000.png'),
                        [24163000] = ICON('icons/24163000.png'),
                        [34001000] = ICON('icons/34001000.png'),
                        [34002000] = ICON('icons/34002000.png'),
                        [35001000] = ICON('icons/35001000.png'),

                        [23043000] = ICON('icons/23043000.png'),--门


                        [23024000] = ICON('icons/23024000.png'),
                        [11401000] = ICON('icons/11401000.png'),
                        [11452000] = ICON('icons/11452000.png'),
                        [23002000] = ICON('icons/23002000.png'),
                        [23018000] = ICON('icons/23018000.png'),
                        [23019000] = ICON('icons/23019000.png'),
                        [24091000] = ICON('icons/24091000.png'),
                        [24093000] = ICON('icons/24093000.png'),
                        [24097000] = ICON('icons/24097000.png'),
                        [23999000] = ICON('icons/23024000.png'),
                    }

                }
            },
            positioning = {
                height_offset = -0.2,
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        --style = 'color_point',
                        style = 'icon',
                        icon = 'icon_red_mark@2x.png',
                        -- icon = '222@2x.png',
                        --color = '0xFFFCD630',
                        enable_alpha = false,
                        size = 2,

                    }
                }
            },
            navigate = {
                height_offset = - 0.3,
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'linestring',
                        color = '0xFF2089e4',
                        line_style = 'SOLID',
                        enable_alpha = false,
                        width = 0.75,
                        has_start=true,
                        has_end=true,
                        has_arrow=true,
                    },
                }
            },
            featurelayer = {
                height_offset = - 0.2,
                renderer = {
                    type = 'simple',
                    ['2d'] = {
                        style = 'icon',
                        icon = 'icons/11452000.png',
                        --color = '0xFF698AE7',
                        enable_alpha = false,
                        width = 25,
                        height = 25,
                        -- has_start=true,
                        --has_end=true,
                        -- has_arrow=true,
                        use_texture_origin_size = false,
                        anchor_x = 0.5,
                        anchor_y = 0.5
                    },
                }
            }

        }
    }

end



CONFIG = {
    views = {
        chinaEx =viewStyle('display'),
        enEx =viewStyle('englishName'),

        default = {
            layers = {
                Frame = {
                    height_offset = 0.1,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'polygon',
                            face = {
                                color = '0xffFDFBEE',--路
                                --texture = "icons/11000000.png",
                                enable_alpha = false,
                                --automatic_scale = true,
                            },
                            outline = {
                                --color = '0xff000000',
                                color = '0xff62619a',--外部边框
                                width = 0.05,
                                enable_alpha = false,
                            },
                            left_side = {}
                        },
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
                            --爆炸
                            [13152002] = COLOR_STYLE('0xffE8E6E7', '0xff7F7F7F'),
                            [11001000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11002000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11003000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11004000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11005000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11006000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11007000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11008000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11009000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11010000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11011000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11012000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11013000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11014000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11015000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11016000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11017000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11018000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11019000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11020000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11021000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11022000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11023000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11201000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11202000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11203000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11204000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11205000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11206000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11207000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11208000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11209000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11210000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11211000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11212000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11213000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11214000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11215000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11216000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11217000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11401000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11402000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11403000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11404000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11405000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11451000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11452000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11453000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11454000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11471000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11472000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11473000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [11491000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [13001000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13002000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13003000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13004000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13005000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13006000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13007000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13008000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13009000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13010000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13011000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13031000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13032000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13033000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13034000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13035000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13036000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13037000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [13051000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13052000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13053000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13054000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13061000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13062000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13063000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13071000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13072000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13073000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13074000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13075000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13076000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13091000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13092000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13093000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13094000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13095000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13096000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13101000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13102000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13103000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13104000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13105000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13111000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13112000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13113000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13114000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13115000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13116000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13117000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13121000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13122000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13123000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13124000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13125000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13126000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13127000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13128000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13129000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13141000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13142000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13143000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13144000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13151000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13152000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13153000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13154000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13155000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13156000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13161000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13162000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13163000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13164000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13165000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13166000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13167000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13168000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13169000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [13170000]=Set3dColorWith('0xffa5b1f2','0xff8a8aae','0xffa5b1f2','0xff8d9be8',5.0),
                            [12001000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12002000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12003000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12004000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12005000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12006000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12007000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12008000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12009000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12010000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12011000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12012000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12013000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12014000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [12015000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14001000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14002000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14003000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14004000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14005000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14006000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14007000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14008000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14009000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14010000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14011000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14012000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14013000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14014000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14015000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15001000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15002000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15003000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15004000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15005000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15006000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15007000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15008000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15009000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15010000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15011000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15012000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15013000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15014000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15015000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15016000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15017000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15018000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15019000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15020000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15021000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15022000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15023000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15024000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15025000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15026000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15027000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15028000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15029000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15030000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15031000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15032000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15033000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15034000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15035000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15036000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15037000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15038000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15039000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15040000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15041000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15042000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15043000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15044000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15045000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15046000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [16001000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16002000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16003000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16004000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16005000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16006000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16007000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16008000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16009000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16010000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16011000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16012000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16013000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16014000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16015000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [16016000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21042000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21023000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21043000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21044000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21045000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21046000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21047000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21048000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21049000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21001000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21002000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21003000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21004000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21005000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21006000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21007000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21008000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21009000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21051000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21010000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21011000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21012000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21013000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21014000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21015000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21016000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21017000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21018000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21019000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21020000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21021000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21022000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21024000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21025000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21026000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21027000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21028000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21029000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21030000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21031000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21032000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21050000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21033000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21034000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21035000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21036000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21037000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21038000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21039000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21040000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [21041000]=Set3dColorWith('0xffd4edff','0xff8a8aae','0xffd4edff','0xffd4e2ff',5.0),
                            [17001000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [17002000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [17003000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [17004000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [17005000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [17006000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [17007000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [17008000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22001000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22002000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22003000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22004000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22006000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22018000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22019000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22020000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22031000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22032000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22039000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22040000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22051000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22011000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22012000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22005000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22013000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22014000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22015000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22016000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22017000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22021000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22022000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22023000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22033000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22034000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22035000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22036000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22037000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22038000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22052000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22053000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22054000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [22055000]=Set3dColorWith('0xfffdebd8','0xff8a8aae','0xfffdebd8','0xfffedebc',5.0),
                            [23001000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23002000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23003000]=Set3dColorWith('0xfff7ebff','0xffe8cff0','0xfff7ebff','0xfff7ebff',5.0),
                            [23004000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23005000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23006000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23007000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23008000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23009000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23010000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23011000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23012000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23013000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23014000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23015000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23016000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23017000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23018000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23019000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23020000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23021000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23022000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23023000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23024000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23025000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23026000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23027000]=Set3dColorWith('0xffeef9ff','0xff8a8aae','0xffeef9ff','0xffeef9ff',5.0),
                            [23028000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23029000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23030000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23031000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23032000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23033000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23034000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23035000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23036000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23037000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23038000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23039000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23040000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23041000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23042000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23043000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23044000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23045000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23046000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23047000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23048000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23049000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23050000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23051000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23052000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23053000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23054000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23055000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23056000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23057000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23058000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23059000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23060000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23061000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23062000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24001000]=Set3dColorWith('0xff99ccff','0xff8a8aae','0xff99ccff','0xff99ccff',5.0),
                            [24002000]=Set3dColorWith('0xff99ccff','0xff8a8aae','0xff99ccff','0xff99ccff',5.0),
                            [24003000]=Set3dColorWith('0xff99ccff','0xff8a8aae','0xff99ccff','0xff99ccff',5.0),
                            [24004000]=Set3dColorWith('0xffbbe0ff','0xff8a8aae','0xffbbe0ff','0xffbbe0ff',5.0),
                            [24005000]=Set3dColorWith('0xffbbe0ff','0xff8a8aae','0xffbbe0ff','0xffbbe0ff',5.0),
                            [24006000]=Set3dColorWith('0xffbbe0ff','0xff8a8aae','0xffbbe0ff','0xffbbe0ff',5.0),
                            [24007000]=Set3dColorWith('0xffbbe0ff','0xff8a8aae','0xffbbe0ff','0xffbbe0ff',5.0),
                            [24008000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24009000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24010000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24011000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24012000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24013000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24014000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24041000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24042000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24043000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24044000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24061000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24062000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24063000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24091000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24092000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24093000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24094000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24095000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24096000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24097000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24098000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24099000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24100000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24111000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24112000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24113000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24114000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24115000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24116000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24117000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24118000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24119000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24120000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24121000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24122000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24141000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24142000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24151000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24152000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24161000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24162000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24163000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [31001000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [31002000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [31003000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [31004000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [32001000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [32002000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [32003000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [32021000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [33001000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [33002000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [33021000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [33022000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [33041000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [33042000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [34001000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [34002000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [34021000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [35001000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [35002000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [35003000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [36001000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [36002000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [37001000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [37002000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),


                            --大类配色
                            [11000000]=Set3dColorWith('0xfff7e9d5','0xff8a8aae','0xfff7e9d5','0xfff9dcb3',5.0),
                            [13000000]=Set3dColorWith('0xffb7c0ef','0xff8a8aae','0xffb7c0ef','0xffa1adec',5.0),
                            [12000000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [14000000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [15000000]=Set3dColorWith('0xffb9cbf9','0xff8a8aae','0xffb9cbf9','0xff9faaf9',5.0),
                            [16000000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [21000000]=Set3dColorWith('0xff92c4ff','0xff8a8aae','0xff92c4ff','0xff9ab5fb',5.0),
                            [17000000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [22000000]=Set3dColorWith('0xffffcaa6','0xff8a8aae','0xffffcaa6','0xfff9bc92',5.0),
                            [23000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [24000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [31000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [32000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [33000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [34000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [35000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [36000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [37000000]=Set3dColorWith('0xffe0ffcc','0xff8a8aae','0xffe0ffcc','0xffd3f8bc',5.0),
                            [23999000] = COLOR_STYLE('0xff7c99d1','0xff7F7F7F'),
                            --爆炸


                        }
                    }
                },
                Area_text = {
                    collision_detection = true,
                    font_path = GET_FONT_PATH(),
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'annotation',
                            color = '0xFF696969',
                            --color = '0xFFc0e5a8',
                            field = 'name',
                            size = 25,
                            outline_color = '0xFFffffab',
                            outline_width = 0.5,
                            anchor_x = 0.5,
                            anchor_y = 0.5,
                            height = 5.0,
                            aabbox_extend = 30,
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
                            [11000000] = ICON('icons/11000000.png'),
                            [11401000] = ICON('icons/11401000.png'),
                            [11454000] = ICON('icons/11454000.png'),
                            [13076000] = ICON('icons/13076000.png'),
                            [13113000] = ICON('icons/13113000.png'),
                            [13116000] = ICON('icons/13116000.png'),
                            [15001000] = ICON('icons/15001000.png'),
                            [15002000] = ICON('icons/15002000.png'),
                            [15026000] = ICON('icons/15026000.png'),
                            [15043000] = ICON('icons/15043000.png'),
                            [15044000] = ICON('icons/15044000.png'),
                            [17001000] = ICON('icons/17001000.png'),
                            [17004000] = ICON('icons/17004000.png'),
                            [17006000] = ICON('icons/17006000.png'),
                            [17007000] = ICON('icons/17007000.png'),
                            [17008000] = ICON('icons/17008000.png'),
                            [21048000] = ICON('icons/21048000.png'),
                            [21049000] = ICON('icons/21049000.png'),
                            [22011000] = ICON('icons/22011000.png'),
                            [22012000] = ICON('icons/22012000.png'),
                            [22014000] = ICON('icons/22014000.png'),
                            [22015000] = ICON('icons/22015000.png'),
                            [22016000] = ICON('icons/22016000.png'),
                            [22017000] = ICON('icons/22017000.png'),
                            [22019000] = ICON('icons/22019000.png'),
                            [22021000] = ICON('icons/22021000.png'),
                            [22022000] = ICON('icons/22022000.png'),
                            [22023000] = ICON('icons/22023000.png'),
                            [22033000] = ICON('icons/22033000.png'),
                            [22039000] = ICON('icons/22039000.png'),
                            [22040000] = ICON('icons/22040000.png'),
                            [22052000] = ICON('icons/22052000.png'),
                            [22053000] = ICON('icons/22053000.png'),
                            [22054000] = ICON('icons/22054000.png'),
                            [22055000] = ICON('icons/22055000.png'),
                            --[23004000] = ICON('icons/23004000.png'),
                            [23005000] = ICON('icons/23005000.png'),
                            [23007000] = ICON('icons/23007000.png'),
                            [23008000] = ICON('icons/23008000.png'),
                            [23009000] = ICON('icons/23009000.png'),
                            [23010000] = ICON('icons/23010000.png'),
                            [23011000] = ICON('icons/23011000.png'),
                            [23012000] = ICON('icons/23012000.png'),
                            [23013000] = ICON('icons/23013000.png'),
                            [23014000] = ICON('icons/23014000.png'),
                            [23015000] = ICON('icons/23015000.png'),
                            [23016000] = ICON('icons/23016000.png'),
                            [23017000] = ICON('icons/23017000.png'),
                            [23018000] = ICON('icons/23018000.png'),
                            [23019000] = ICON('icons/23019000.png'),
                            [23020000] = ICON('icons/23020000.png'),
                            [23021000] = ICON('icons/23021000.png'),
                            [23022000] = ICON('icons/23022000.png'),
                            [23023000] = ICON('icons/23023000.png'),
                            [23024000] = ICON('icons/23024000.png'),
                            [23025000] = ICON('icons/23025000.png'),
                            [23026000] = ICON('icons/23026000.png'),
                            [23027000] = ICON('icons/23027000.png'),
                            [23028000] = ICON('icons/23028000.png'),
                            [23029000] = ICON('icons/23029000.png'),
                            [23030000] = ICON('icons/23030000.png'),
                            [23031000] = ICON('icons/23031000.png'),
                            [23032000] = ICON('icons/23032000.png'),
                            [23033000] = ICON('icons/23033000.png'),
                            [23034000] = ICON('icons/23034000.png'),
                            [23035000] = ICON('icons/23035000.png'),
                            [23036000] = ICON('icons/23036000.png'),
                            [23037000] = ICON('icons/23037000.png'),
                            [23038000] = ICON('icons/23038000.png'),
                            [23039000] = ICON('icons/23039000.png'),
                            [23040000] = ICON('icons/23040000.png'),
                            [23041000] = ICON('icons/23041000.png'),
                            [23042000] = ICON('icons/23042000.png'),
                            [23059000] = ICON('icons/23059000.png'),
                            [23060000] = ICON('icons/23060000.png'),
                            [23061000] = ICON('icons/23061000.png'),
                            [24003000] = ICON('icons/24003000.png'),
                            [24006000] = ICON('icons/24006000.png'),
                            [24014000] = ICON('icons/24014000.png'),
                            [24091000] = ICON('icons/24091000.png'),
                            [24092000] = ICON('icons/24092000.png'),
                            [24093000] = ICON('icons/24093000.png'),
                            [24094000] = ICON('icons/24094000.png'),
                            [24097000] = ICON('icons/24097000.png'),
                            [24098000] = ICON('icons/24098000.png'),
                            [24099000] = ICON('icons/24099000.png'),
                            [24100000] = ICON('icons/24100000.png'),
                            [24111000] = ICON('icons/24111000.png'),
                            [24112000] = ICON('icons/24112000.png'),
                            [24113000] = ICON('icons/24113000.png'),
                            [24114000] = ICON('icons/24114000.png'),
                            [24115000] = ICON('icons/24115000.png'),
                            [24116000] = ICON('icons/24116000.png'),
                            [24117000] = ICON('icons/24117000.png'),
                            [24118000] = ICON('icons/24118000.png'),
                            [24119000] = ICON('icons/24119000.png'),
                            [24120000] = ICON('icons/24120000.png'),
                            [24121000] = ICON('icons/24121000.png'),
                            [24141000] = ICON('icons/24141000.png'),
                            [24142000] = ICON('icons/24142000.png'),
                            [24151000] = ICON('icons/24151000.png'),
                            [24152000] = ICON('icons/24152000.png'),
                            [24161000] = ICON('icons/24161000.png'),
                            [24162000] = ICON('icons/24162000.png'),
                            [24163000] = ICON('icons/24163000.png'),
                            [34001000] = ICON('icons/34001000.png'),
                            [34002000] = ICON('icons/34002000.png'),
                            [35001000] = ICON('icons/35001000.png'),
                        }
                    }
                },
                positioning = {
                    height_offset = -0.4,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'icon',

                            icon = 'icon_red_mark@2x.png',
                            enable_alpha = false,
                            use_texture_origin_size = true
                            --              width=80,
                            --            height=80,
                            --              size = 3,
                        },
                        ['3d'] = {
                            style = 'icon',

                            icon = 'locationPoint.png',
                            enable_alpha = true,
                            height = 5,
                            top_edge_width = 0,
                            bottom_edge_width = 0,
                            --              size = 3,
                        },
                    }
                },
                navigate = {
                    height_offset = - 0.3,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'linestring',
                            color = '0xFF2089e4',
                            line_style = 'SOLID',
                            enable_alpha = false,
                            width = 0.05,
                            has_start=true,
                            has_end=true,
                            has_arrow=true,
                        },
                    }
                },
                tempfeature = {
                    height_offset = -0.4,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'icon',

                            icon = 'icon_red_mark@2x.png',
                            enable_alpha = false,
                            use_texture_origin_size = true
                            --              width=80,
                            --            height=80,
                            --              size = 3,
                        },

                    }
                },
            }
        },

        defaultexhibition = {
            layers = {
                Frame = {
                    height_offset = 0.1,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'polygon',
                            face = {
                                --color = '0xfffafafa',--路
                                color = '0xffFDFBEE',
                                enable_alpha = false,
                            },
                            outline = {
                                --color = '0xff000000',
                                color = '0xff000000',--外部边框
                                width = 0.1,
                                enable_alpha = false,
                            },
                            left_side = {}
                        },
                    }
                },
                Area = {
                    height_offset = 0,
                    renderer = {
                        type = 'unique',
                        key = {
                            'name',
                            'id',
                            'category',
                        },
                        default = DEFAULT_STYLE(),
                        styles = {





                        }
                    }
                },
                Area_text = {
                    collision_detection = true,
                    font_path = GET_FONT_PATH(),
                    --font_path = GET_YUAN_FONT_PATH(),
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'annotation',
                            color = '0xFF696969',
                            --color = '0xFF000000',
                            aabbox_extend = 30,
                            width = 50,
                            field = 'display',
                            size = 25,
                            outline_color = '0xFF000000',
                            outline_width = 0,
                            anchor_x = 0.5,
                            anchor_y = 0.5

                        },
                    }
                },
                Facility = {
                    collision_detection=true;
                    height_offset = -0.2;
                    renderer = {
                        type = 'unique',
                        key = {
                            'category'
                        },
                        default = DEFAULT_ICON(),
                        styles = {
                            [11000000] = ICON('icons/11000000.png'),
                            [11401000] = ICON('icons/11401000.png'),
                            [11454000] = ICON('icons/11454000.png'),
                            [13076000] = ICON('icons/13076000.png'),
                            [13113000] = ICON('icons/13113000.png'),
                            [13116000] = ICON('icons/13116000.png'),
                            [15001000] = ICON('icons/15001000.png'),
                            [15002000] = ICON('icons/15002000.png'),
                            [15026000] = ICON('icons/15026000.png'),
                            [15043000] = ICON('icons/15043000.png'),
                            [15044000] = ICON('icons/15044000.png'),
                            [17001000] = ICON('icons/17001000.png'),
                            [17004000] = ICON('icons/17004000.png'),
                            [17006000] = ICON('icons/17006000.png'),
                            [17007000] = ICON('icons/17007000.png'),
                            [17008000] = ICON('icons/17008000.png'),
                            [21048000] = ICON('icons/21048000.png'),
                            [21049000] = ICON('icons/21049000.png'),
                            [22011000] = ICON('icons/22011000.png'),
                            [22012000] = ICON('icons/22012000.png'),
                            [22014000] = ICON('icons/22014000.png'),
                            [22015000] = ICON('icons/22015000.png'),
                            [22016000] = ICON('icons/22016000.png'),
                            [22017000] = ICON('icons/22017000.png'),
                            [22019000] = ICON('icons/22019000.png'),
                            [22021000] = ICON('icons/22021000.png'),
                            [22022000] = ICON('icons/22022000.png'),
                            [22023000] = ICON('icons/22023000.png'),
                            [22033000] = ICON('icons/22033000.png'),
                            [22039000] = ICON('icons/22039000.png'),
                            [22040000] = ICON('icons/22040000.png'),
                            [22052000] = ICON('icons/22052000.png'),
                            [22053000] = ICON('icons/22053000.png'),
                            [22054000] = ICON('icons/22054000.png'),
                            [22055000] = ICON('icons/22055000.png'),
                            [23004000] = ICON('icons/23004000.png'),
                            [23005000] = ICON('icons/23005000.png'),
                            [23007000] = ICON('icons/23007000.png'),
                            [23008000] = ICON('icons/23008000.png'),
                            [23009000] = ICON('icons/23009000.png'),
                            [23010000] = ICON('icons/23010000.png'),
                            [23011000] = ICON('icons/23011000.png'),
                            [23012000] = ICON('icons/23012000.png'),
                            [23013000] = ICON('icons/23013000.png'),
                            [23014000] = ICON('icons/23014000.png'),
                            [23015000] = ICON('icons/23015000.png'),
                            [23016000] = ICON('icons/23016000.png'),
                            [23017000] = ICON('icons/23017000.png'),
                            [23018000] = ICON('icons/23018000.png'),
                            [23019000] = ICON('icons/23019000.png'),
                            [23020000] = ICON('icons/23020000.png'),
                            [23021000] = ICON('icons/23021000.png'),
                            [23022000] = ICON('icons/23022000.png'),
                            [23023000] = ICON('icons/23023000.png'),
                            [23024000] = ICON('icons/toilet.png'),
                            [23025000] = ICON('icons/toilet.png'),
                            [23026000] = ICON('icons/23026000.png'),
                            [23027000] = ICON('icons/23027000.png'),
                            [23028000] = ICON('icons/23028000.png'),
                            [23029000] = ICON('icons/23029000.png'),
                            [23030000] = ICON('icons/23030000.png'),
                            [23031000] = ICON('icons/23031000.png'),
                            [23032000] = ICON('icons/23032000.png'),
                            [23033000] = ICON('icons/23033000.png'),
                            [23034000] = ICON('icons/23034000.png'),
                            [23035000] = ICON('icons/23035000.png'),
                            [23036000] = ICON('icons/23036000.png'),
                            [23037000] = ICON('icons/23037000.png'),
                            [23038000] = ICON('icons/23038000.png'),
                            [23039000] = ICON('icons/23039000.png'),
                            [23040000] = ICON('icons/23040000.png'),
                            [23041000] = ICON('icons/23041000.png'),
                            [23042000] = ICON('icons/23042000.png'),
                            [23059000] = ICON('icons/23059000.png'),
                            [23060000] = ICON('icons/23060000.png'),
                            [23061000] = ICON('icons/23061000.png'),
                            [24003000] = ICON('icons/24003000.png'),
                            [24006000] = ICON('icons/24006000.png'),
                            [24014000] = ICON('icons/24014000.png'),
                            [24091000] = ICON('icons/24091000.png'),
                            [24092000] = ICON('icons/24092000.png'),
                            [24093000] = ICON('icons/24093000.png'),
                            [24094000] = ICON('icons/24094000.png'),
                            [24097000] = ICON('icons/24097000.png'),
                            [24098000] = ICON('icons/24098000.png'),
                            [24099000] = ICON('icons/24099000.png'),
                            [24100000] = ICON('icons/24100000.png'),
                            [24111000] = ICON('icons/24111000.png'),
                            [24112000] = ICON('icons/24112000.png'),
                            [24113000] = ICON('icons/24113000.png'),
                            [24114000] = ICON('icons/24114000.png'),
                            [24115000] = ICON('icons/24115000.png'),
                            [24116000] = ICON('icons/24116000.png'),
                            [24117000] = ICON('icons/24117000.png'),
                            [24118000] = ICON('icons/24118000.png'),
                            [24119000] = ICON('icons/24119000.png'),
                            [24120000] = ICON('icons/24120000.png'),
                            [24121000] = ICON('icons/24121000.png'),
                            [24141000] = ICON('icons/24141000.png'),
                            [24142000] = ICON('icons/24142000.png'),
                            [24151000] = ICON('icons/24151000.png'),
                            [24152000] = ICON('icons/24152000.png'),
                            [24161000] = ICON('icons/24161000.png'),
                            [24162000] = ICON('icons/24162000.png'),
                            [24163000] = ICON('icons/24163000.png'),
                            [34001000] = ICON('icons/34001000.png'),
                            [34002000] = ICON('icons/34002000.png'),
                            [35001000] = ICON('icons/35001000.png'),
                            [23041000] = ICON('icons/23043000.png'),--门
                            [23043000] = ICON('icons/23043000.png'),--门
                        }

                    }
                },
                positioning = {
                    height_offset = -0.2,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            --style = 'color_point',
                            style = 'icon',
                            icon = 'icon_red_mark@2x.png',
                            --color = '0xFFFCD630',
                            enable_alpha = false,
                            size = 2,

                        }
                    }
                },
                navigate = {
                    height_offset = - 0.3,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'linestring',
                            color = '0xFF698AE7',
                            line_style = 'SOLID',
                            enable_alpha = false,
                            width = 0.75,
                            has_start=true,
                            has_end=true,
                            has_arrow=true,
                        },
                    }
                },
                featurelayer = {
                    height_offset = - 0.2,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'icon',
                            icon = 'icons/17004000.png',
                            --color = '0xFF698AE7',
                            enable_alpha = false,
                            width = 20,
                            height = 20,
                            --size = 2,
                            -- has_start=true,
                            --has_end=true,
                            -- has_arrow=true,
                        },
                    }
                }

            }
        }
    }
}







