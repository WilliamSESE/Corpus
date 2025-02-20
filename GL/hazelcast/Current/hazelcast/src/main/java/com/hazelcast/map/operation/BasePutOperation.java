/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.map.operation;

import com.hazelcast.core.EntryEventType;
import com.hazelcast.core.EntryView;
import com.hazelcast.map.EntryViews;
import com.hazelcast.map.MapEventPublisher;
import com.hazelcast.map.MapServiceContext;
import com.hazelcast.map.record.Record;
import com.hazelcast.map.record.RecordInfo;
import com.hazelcast.map.record.Records;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.spi.BackupAwareOperation;
import com.hazelcast.spi.Operation;
import com.hazelcast.spi.ResponseHandler;

public abstract class BasePutOperation extends LockAwareOperation implements BackupAwareOperation {

    protected transient Data dataOldValue;
    protected transient EntryEventType eventType;

    public BasePutOperation(String name, Data dataKey, Data value) {
        super(name, dataKey, value, -1);
    }

    public BasePutOperation(String name, Data dataKey, Data value, long ttl) {
        super(name, dataKey, value, ttl);
    }

    public BasePutOperation() {
    }

    public void afterRun() {
        final MapServiceContext mapServiceContext = mapService.getMapServiceContext();
        final MapEventPublisher mapEventPublisher = mapServiceContext.getMapEventPublisher();
        mapServiceContext.interceptAfterPut(name, dataValue);
        eventType = getEventType();
        mapEventPublisher.publishEvent(getCallerAddress(), name, eventType, dataKey, dataOldValue, dataValue);
        invalidateNearCaches();
        if (mapContainer.getWanReplicationPublisher() != null && mapContainer.getWanMergePolicy() != null) {
            Record record = recordStore.getRecord(dataKey);
            if (record == null) {
                return;
            }
            final Data valueConvertedData = mapServiceContext.toData(dataValue);
            final EntryView entryView = EntryViews.createSimpleEntryView(dataKey, valueConvertedData, record);
            mapEventPublisher.publishWanReplicationUpdate(name, entryView);
        }
    }

    private EntryEventType getEventType() {
        if (eventType == null) {
            eventType = dataOldValue == null ? EntryEventType.ADDED : EntryEventType.UPDATED;
        }
        return eventType;
    }

    public boolean shouldBackup() {
        return true;
    }

    public Operation getBackupOperation() {
        Record record = recordStore.getRecord(dataKey);
        RecordInfo replicationInfo = Records.buildRecordInfo(record);
        return new PutBackupOperation(name, dataKey, dataValue, replicationInfo);
    }

    public final int getAsyncBackupCount() {
        return mapContainer.getAsyncBackupCount();
    }

    public final int getSyncBackupCount() {
        return mapContainer.getBackupCount();
    }

    public void onWaitExpire() {
        final ResponseHandler responseHandler = getResponseHandler();
        responseHandler.sendResponse(null);
    }

    @Override
    public String toString() {
        return "BasePutOperation{" + name + "}";
    }
}
