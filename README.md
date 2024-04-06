# PotatoIpDisplay

一款用于显示玩家 Ip 归属地的 Bukkit 插件。

## 使用文档（中文）
详见 [此处](https://upt.curiousers.org/docs/PotatoIpDisplay/intro)

## Placeholder API

| 变量                           | 描述          | 返回内容(`ip2region`) | 返回内容(`pconline`) |
|:-----------------------------|:------------|:------------------|:-----------------|
| `%potatoipdisplay_ip%`       | 玩家的 IP      | `11.45.1.4`       | `11.45.1.4`      |
| `%potatoipdisplay_country%`  | IP 所属国家     | `中国`              | `中国`             |
| `%potatoipdisplay_province%` | IP 所属省份     | `上海`              | `上海市`            |
| `%potatoipdisplay_city%`     | IP 所属城市     | `上海`              | `上海`             |
| `%potatoipdisplay_region%`   | IP 所属区域     | `0`               | ` `              |
| `%potatoipdisplay_isp%`      | 运营商信息       | `联通`              | `上海市 联通`         |
| `%potatoipdisplay_fallback%` | fallback 变量 | `上海`              | `上海`             |

fallback 变量适用于一些无法查询到对应信息的情况。插件将从 `省份` -> `国家` -> `城市` 依次查询并返回第一个有效结果，从而在最大程度上避免返回未知值。

![demo](assets/papidemo.png)

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