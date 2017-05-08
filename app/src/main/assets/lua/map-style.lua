local function GET_FONT_PATH()
    local engine = GetEngine()
    local properties = engine.properties
    local os = properties["os"]
    if os then
        if os >= "iOS/7.0" and os < "iOS/8.0" then
            return "/System/Library/Fonts/Cache/STHeiti-Light.ttc"
        elseif os >= "iOS/8.0" and os < "iOS/9.0" then
            return "/System/Library/Fonts/Core/STHeiti-Light.ttc"
        elseif os >= "iOS/9.0" then
            return "/System/Library/Fonts/LanguageSupport/PingFang.ttc"
        else
            return "/System/Library/Fonts/LanguageSupport/PingFang.ttc"
        end
    else
        return properties["lua_path"] .. "/DroidSansFallback.ttf"
    end
end

-- Android
local function GET_CACHE_PATH()
    local engine = GetEngine()
    local properties = engine.properties

    return properties["cache_folder"]
end

local function Set3dColorWith(color, widthColor, topcolor, sidecolor, temph)

    if (temph == 0) then
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
                color = widthColor, --内部边框
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
                color = '0xffffffaa',
                width = 0.03,
                height = temph,
                enable_alpha = false,
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

local function Set2dColorWith(color, widthColor, leftcolor, rightcolor, height)
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

local function Set2dColor(color, widthColor, width)
    return {
        ['2d'] = {
            style = 'polygon',
            face = {
                enable_alpha = true,
                color = color,
            },
            outline = {
                color = widthColor,
                width = width,
                enable_alpha = true,
            },
            left_side = {}
        },
    }
end

local function SetImageWith(imageName, needTiled, angle, needAligment)
    -- body
    return {
        ['2d'] = {
            style = 'polygon',
            face = {
                enable_alpha = false,
                texture = imageName,
                automatic_scale = false,
                texture_rotation = angle,
                edge_aligment = needAligment,
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
                --        color = '0xffebebeb',--默认颜色
                color = '0xfffffff8', --默认颜色
                enable_alpha = true,
                texture = null,
                automatic_scale = null
            },
            outline = {
                color = '0xfffffff8',
                --        color = '0xffebebeb',--默认颜色
                width = 0.5,
                enable_alpha = true,
            },
            left_side = {}
        }
    }
end

local function DEFAULT_STYLE_MY()
    return {
        ['2d'] = {
            style = 'polygon',
            face = {
                color = '0xff0196d0', --默认颜色
                enable_alpha = false,
                texture = null,
                automatic_scale = null
            },
            outline = {
                color = '0xff0196d0',
                width = 0.1,
                enable_alpha = false,
            },
            left_side = {}
        }
    }
end

local function MULTIPOINT_STYLE()
    return {
        ['2d'] = {
            style = 'multipoint',
            size = 1,
            color = '0xFFCD5E40',
            shape = 'Circle', -- Circle,Square
        },
    }
end

local function DEFAULT_3D_STYLE()
    return {
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
                enable_alpha = false,
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

local function DEFAULT_3D_STYLE(a, height)
    return {
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
                enable_alpha = false,
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

local function COLOR_STYLE(a, b, c)
    style = DEFAULT_STYLE()
    style['2d'].face.color = a;
    style['2d'].face.enable_alpha = false;
    style['2d'].outline.color = b or '0xFFc0c0c0';
    style['2d'].outline.width = c or 0.02;
    style['2d'].outline.enable_alpha = false;
    return style
end

local function COLOR_3D_STYLE(a, b, c, height)
    style = DEFAULT_3D_STYLE(a, height)
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
            unit = 'pt', -- 图标大小(width、height)使用的单位,"px"表示像素,"pt"表示1/72英寸
            width = 8,
            height = 8,
            anchor_x = 0.5,
            anchor_y = 0.5
        }
    }
end

local function ICON(a)
    return {
        ['2d'] = {
            style = 'icon',
            icon = a,
            use_texture_origin_size = false,
            unit = 'pt', -- 图标大小(width、height)使用的单位,"px"表示像素,"pt"表示1/72英寸
            width = 8,
            height = 8,
            anchor_x = 0.5,
            anchor_y = 0.5
        }
    }
end

local function DEFAULT_TEXT()
    return {
        ['2d'] = {
            style = 'annotation',
            color = '0xFF343434',
            field = 'display',
            unit = 'pt', -- 大小(width、height)使用的单位,"px"表示像素,"pt"表示1/72英寸
            size = 6,
            outline_color = '0x00ffffff',
            outline_width = 0.5,
            anchor_x = 0.5,
            anchor_y = 0.5,
            height = 5.0,
            aabbox_extend = 15, --外包盒扩大像素数，用于扩大碰撞检测范围
        }
    }
end

CONFIG = {
    views = {
        default = {
            layers = {
                Frame = {
                    height_offset = 0,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'polygon',
                            face = {
                                color = '0xffd4f2b5', --路
                                enable_alpha = false,
                            },
                            outline = {
                                --                color = '0xff000000',
                                color = '0xfff1f0cf', --外部边框
                                width = 0.5,
                                enable_alpha = false,
                            },
                            left_side = {}
                        },
                    }
                },
                poi = {
                    height_offset = 0,
                    renderer = {
                        type = 'unique',
                        key = {
                            'id', --
                            'name',
                            'category',
                        },
                        default = {
                            ['2d'] = {
                                style = 'polygon',
                                face = {
                                    color = '0xff0196d0', --默认颜色
                                    enable_alpha = true,
                                    texture = null,
                                    automatic_scale = null
                                },
                                outline = {
                                    color = '0xff0196d0',
                                    width = 0.1,
                                    enable_alpha = true,
                                },
                                left_side = {}
                            }
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
                            'name',
                            'display',
                        },
                        default = DEFAULT_STYLE(),
                        styles = {
                            [23999000] = Set2dColor('0xffebebeb', '0xffbababa', 0.15);
                            [24091000] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15);
                            [24097000] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15);
                            [23024000] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15);
                            [23063000] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15);
                            [23059000] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15);
                            [23024000] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15);
                            [23025000] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15);
                            [23018000] = Set2dColor('0xfffbebee', '0xfff3bdc8', 0.15);
                            [15000000] = Set2dColor('0xfffbebee', '0xfff3bdc8', 0.15);
                            [17004000] = Set2dColor('0xfffffff8', '0xffaf967e', 0.15);
                            [22001000] = Set2dColor('0xffe6edf6', '0xffb2c8e4', 0.15);
                            [23001000] = Set2dColor('0xffe6f2e7', '0xffb5d9b8', 0.15);
                            [23027000] = Set2dColor('0xfff1e8f6', '0xffd1b5e2', 0.15);
                            [24007000] = Set2dColor('0xffcce0de', '0xffcce0de', 0.15);
                            [24008000] = Set2dColor('0xffc7c7c7', '0xffc7c7c7', 0.15);
                            [23040000] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15); --饮水间
                            ['\\冲之大道'] = Set2dColor('0xfffcf2b4', '0xfffcf2b4', 0);
                            ['\\五和大道'] = Set2dColor('0xfffcf2b4', '0xfffcf2b4', 0);
                            ['\\贝尔路'] = Set2dColor('0xfffcf2b4', '0xfffcf2b4', 0);
                            ['\\隆平路'] = Set2dColor('0xfffcf2b4', '0xfffcf2b4', 0);
                            ['\\培训'] = Set2dColor('0xffef1e8f6', '0xffd1b5e2', 0.15);
                            ['\\实验'] = Set2dColor('0xffef1e8f6', '0xffd1b5e2', 0.15);
                            ['\\机房'] = Set2dColor('0xffef1e8f6', '0xffd1b5e2', 0.15);
                            ['\\办公室'] = Set2dColor('0xffe6edf6', '0xffb2c8e4', 0.15);
                            ['\\办公区'] = Set2dColor('0xffe6edf6', '0xffb2c8e4', 0.15);
                            ['\\办公楼'] = Set2dColor('0xfffffff8', '0xfffffff8', 0.15);
                            ['\\接待处'] = Set2dColor('0xfffffff8', '0xfffffff8', 0.15);
                            ['\\H2办公楼'] = Set2dColor('0xfffcf2e2', '0xfff7d4a3', 0.15);
                            [1284129] = Set2dColor('0x00fffff8', '0x00fffff8', 0.15);
                            [1284127] = Set2dColor('0x00fffff8', '0x00fffff8', 0.15);
                            [1583138] = Set2dColor('0x00fffff8', '0x00fffff8', 0);
                            [24002000] = Set2dColor('0xffc8dedc', '0xffe6f0ef', 0.05);
                            [24005000] = Set2dColor('0xffcce0de', '0xffcce0de', 0);
                            ['1490708'] = {
                                --走廊
                                ['2d'] = {
                                    style = 'polygon',
                                    face = {
                                        color = '0xfffffff8',
                                        enable_alpha = false,
                                    },
                                    outline = {
                                        color = '0xffA7C2D3',
                                        width = 0.5,
                                        alignment = 'AlignLeft',
                                    },
                                    left_side = {}
                                }
                            },
                            ['1270158'] = {
                                --H1
                                ['2d'] = {
                                    style = 'polygon',
                                    face = {
                                        color = '0xffe6edf6',
                                        enable_alpha = false,
                                    },
                                    outline = {
                                        color = '0xffA7C2D3',
                                        width = 0.5,
                                        alignment = 'AlignLeft',
                                    },
                                    left_side = {}
                                }
                            },
                            ['1270162'] = {
                                --H3
                                ['2d'] = {
                                    style = 'polygon',
                                    face = {
                                        color = '0xffe6edf6',
                                        enable_alpha = false,
                                    },
                                    outline = {
                                        color = '0xffA7C2D3',
                                        width = 0.5,
                                        alignment = 'AlignLeft',
                                    },
                                    left_side = {}
                                }
                            },
                            ['1270166'] = {
                                --H4
                                ['2d'] = {
                                    style = 'polygon',
                                    face = {
                                        color = '0xffe6edf6',
                                        enable_alpha = false,
                                    },
                                    outline = {
                                        color = '0xffA7C2D3',
                                        width = 0.5,
                                        alignment = 'AlignLeft',
                                    },
                                    left_side = {}
                                }
                            },
                            ['1284122'] = {
                                --H 停车场
                                ['2d'] = {
                                    style = 'polygon',
                                    face = {
                                        color = '0xffe6edf6',
                                        enable_alpha = false,
                                    },
                                    outline = {
                                        color = '0xffA7C2D3',
                                        width = 0.5,
                                        alignment = 'AlignLeft',
                                    },
                                    left_side = {}
                                }
                            },
                        }
                    }
                },
                Area_text = {
                    collision_detection = true,
                    font_path = GET_FONT_PATH(),
                    --font_path = lua_path,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'annotation',
                            color = '0xFF343434',
                            field = 'display',
                            unit = 'pt', -- 大小(width、height)使用的单位,"px"表示像素,"pt"表示1/72英寸
                            size = 6,
                            outline_color = '0x00ffffff',
                            outline_width = 0.5,
                            anchor_x = 0.5,
                            anchor_y = 0.5,
                            height = 5.0,
                            aabbox_extend = 15, --外包盒扩大像素数，用于扩大碰撞检测范围
                        },
                        --                        key = {
                        --                            'category',
                        --                            'display',
                        --                        },
                        --                        default = DEFAULT_TEXT(),
                        --                        styles = {
                        --
                        --                            ['null'] = {
                        --                                ['2d'] = {
                        --                                    style = 'annotation',
                        --                                    color = '0x00ffffff',
                        --                                    field = 'display',
                        --                                    unit = 'pt', -- 大小(width、height)使用的单位,"px"表示像素,"pt"表示1/72英寸
                        --                                    size = 6,
                        --                                    outline_color = '0x00ffffff',
                        --                                    outline_width = 0.5,
                        --                                    anchor_x = 0.5,
                        --                                    anchor_y = 0.5,
                        --                                    height = 5.0,
                        --                                    aabbox_extend = 15, --外包盒扩大像素数，用于扩大碰撞检测范围
                        --                                }
                        --                            },
                        --                            ['ICS办公区'] = {
                        --                                ['2d'] = {
                        --                                    style = 'annotation',
                        --                                    color = '0x00ffffff',
                        --                                    field = 'display',
                        --                                    unit = 'pt', -- 大小(width、height)使用的单位,"px"表示像素,"pt"表示1/72英寸
                        --                                    size = 6,
                        --                                    outline_color = '0x00ffffff',
                        --                                    outline_width = 0.5,
                        --                                    anchor_x = 0.5,
                        --                                    anchor_y = 0.5,
                        --                                    height = 5.0,
                        --                                    aabbox_extend = 15, --外包盒扩大像素数，用于扩大碰撞检测范围
                        --                                }
                        --                            }
                        --                            [24005000] = {  --假设匹配到了category为4018000000的Feature，那么使用下面设置的样式
                        --                                           ['2d'] = {
                        --                             style='nullstyle',
                        --                             }

                        --                             ['2d'] = {
                        --                             style = 'annotation',
                        --                             color = '0x00343434',
                        --                             field = 'display',
                        --                             unit = 'pt', -- 大小(width、height)使用的单位,"px"表示像素,"pt"表示1/72英寸
                        --                             size = 6,
                        --                             outline_color = '0x00ffffff',
                        --                             outline_width = 0.5,
                        --                             anchor_x = 0.5,
                        --                             anchor_y = 0.5,
                        --                             height = 5.0,
                        --                             aabbox_extend = 15, --外包盒扩大像素数，用于扩大碰撞检测范围
                        --                             }
                        --                             },
                        --                        }
                    }
                },
                Facility = {
                    height_offset = -0.2;
                    collision_detection = true,
                    renderer = {
                        type = 'unique',
                        key = {
                            'display', 'category'
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
                            [23043000] = ICON('icons/23043000.png'),
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
                            [22006000] = ICON('icons/22006000.png'),
                            [23063000] = ICON('icons/23063000.png'),
                        }
                    }
                },
                positioning = {
                    height_offset = 0,
                    renderer = {
                        type = 'simple',
                        ['2d'] = {
                            style = 'icon',
                            icon = 'locationPoint.png',
                            enable_alpha = true,
                            use_texture_origin_size = true
                        },
                        ['3d'] = {
                            style = 'icon',
                            icon = 'locationPoint.png',
                            enable_alpha = true,
                            height = 5,
                            top_edge_width = 0,
                            bottom_edge_width = 0,
                        },
                    }
                },
                navigate = {
                    height_offset = 0,
                    renderer = {
                        type = 'unique',
                        key = {
                            'navi_name', -- 经停电默认使用这个字段区别导航线和经停点
                        },
                        default = {
                            ['2d'] = {
                                style = 'linestring',
                                color = '0xFF0e89dc', -- 颜色
                                width = 3, -- 线宽
                                line_style = 'NONE', -- 线型，NONE、ARROW、DASHED
                                has_arrow = true, -- 是否绘制方向指示箭头，仅在line_style为NONE时有效
                                has_start = true, -- 绘制起始点
                                has_end = true, -- 绘制终点
                                automatic_scale = false, -- 导航线自适应地图大小
                            },
                        },
                        styles = {
                            ["transit"] = MULTIPOINT_STYLE(), -- 这个是固定匹配经停点的属性
                        }
                    }
                },
            }
        },
    }
}