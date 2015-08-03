/*
 * Copyright 2013-2015 Tsukasa Kitachi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.kxbmap.configs.instance

import com.github.kxbmap.configs.Configs
import java.net.{InetAddress, InetSocketAddress}

trait JavaNetConfigs {

  implicit lazy val inetAddressConfigs: Configs[InetAddress] = Configs.wrapNonFatal { (c, p) =>
    InetAddress.getByName(c.getString(p))
  }


  implicit lazy val inetSocketAddressConfigs: Configs[InetSocketAddress] = Configs.wrapNonFatal { (c, p) =>
    val cc = c.getConfig(p)
    val port = cc.getInt("port")
    Configs[Option[String]].get(cc, "hostname").fold {
      new InetSocketAddress(Configs[InetAddress].get(cc, "addr"), port)
    } {
      hostname => new InetSocketAddress(hostname, port)
    }
  }

}