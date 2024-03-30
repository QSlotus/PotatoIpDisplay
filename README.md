# PotatoIpDisplay

一款用于显示玩家Ip归属地的Minecraft插件，支持 Bukkit 和 Velocity

## Placeholder API

| 变量                           | 描述      | 返回示例        |
|:-----------------------------|:--------|:------------|
| `%potatoipdisplay_ip%`       | 玩家的 IP  | `11.45.1.4` |
| `%potatoipdisplay_country%`  | IP 所属国家 | `中国`        |
| `%potatoipdisplay_province%` | IP 所属省份 | `上海`        |
| `%potatoipdisplay_city%`     | IP 所属城市 | `上海`        |
| `%potatoipdisplay_district%` | IP 所属区  | `0`（可能无法识别） |
| `%potatoipdisplay_isp%`      | 运营商信息   | `联通`        |

## 配置文件（中文）

```
# PotatoIpDisplay-bukkit 配置文件
# 一款用于显示玩家 IP 归属地的 Minecraft 插件
# 详情：[https://github.com/dmzz-yyhyy/PotatoIpDisplay]

# 请勿修改！
config-version: 1

# 常规设置
options:
  # 查询模式: "pconline"（在线 API）或 "ip2region"（本地）
  # "ip2region" 模式为本地查询，插件会自动保存内置的文件至对应路径。
  # 也可以从 [https://github.com/lionsoul2014/ip2region/tree/master/data] 下载
  mode: "ip2region"
  # 开启 bStats 统计
  allow-bstats: true

# 消息设置
messages:
  player-chat:
    # 是否接管玩家消息事件。
    # 与其他消息格式化插件冲突。如有，设为 false 并开启 PlaceholderAPI 支持。
    enabled: true
    # 消息格式
    string: "§7[§b%ipAttr%§7] §f%playerName% §7>> §f%msg%"

  player-login:
    # 是否在玩家登录后发送一条消息显示 IP 归属地。
    enabled: true
    # 消息格式
    string: "§7[§6PotatoIpDisplay§7] §e您当前IP归属地 §7[§b%ipAttr%§7]"

# Placeholder API 设置
papi:
  # 启用 PAPI 支持。
  enabled: false
  # 可用变量：
  # [https://github.com/dmzz-yyhyy/PotatoIpDisplay#placeholder-api]
```
## bStats
<a href="https://bstats.org/plugin/bukkit/PotatoIpDisplay/21473">![https://bstats.org/plugin/bukkit/PotatoIpDisplay/21473](https://bstats.org/signatures/bukkit/PotatoIpDisplay.svg)</a>

## LICENSE
```
Copyright (C) 2024 by NightFish <hk198580666@outlook.com>
Copyright (C) 2024 by yukonisen <yukonisen@curiousers.org>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
```