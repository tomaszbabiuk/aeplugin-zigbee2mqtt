/*
 * Copyright (c) 2019-2022 Tomasz Babiuk
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.automateeverything.zigbee2mqttplugin.ports

import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.hardware.PortCapabilities
import eu.automateeverything.domain.hardware.TimeStamp
import eu.automateeverything.zigbee2mqttplugin.data.UpdatePayload
import java.math.BigDecimal
import java.util.*

class ZigbeeActionInputPort(
    factoryId: String,
    adapterId: String,
    portId: String,
    eventBus: EventBus,
    sleepInterval: Long,
    lastSeenTimestamp: Long,
    readTopic: String,
    private val action: String,
) :
    ZigbeePort<TimeStamp>(
        factoryId,
        adapterId,
        portId,
        eventBus,
        TimeStamp::class.java,
        PortCapabilities(canRead = true, canWrite = false),
        sleepInterval,
        lastSeenTimestamp,
        readTopic,
        TimeStamp(BigDecimal.ZERO)
    ) {

    override fun tryUpdateInternal(payload: UpdatePayload): Boolean {
        if (payload.action != null && payload.action == action) {
            val now = Calendar.getInstance()
            value = TimeStamp.fromCalendar(now)
            return true
        }

        return false
    }
}
