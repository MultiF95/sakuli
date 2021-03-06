/*
 * Sakuli - Testing and Monitoring-Tool for Websites and common UIs.
 *
 * Copyright 2013 - 2018 the original author or authors.
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

package org.sakuli.services.forwarder.configuration;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Created by georgi on 23/02/18.
 */
public class UnixTimestampConverterFunctionTest {

    @DataProvider
    public Object[][] convertToUnixTimestampDP() {
        return new Object[][]{
                {new Date(1519377570), "1519377.570"},
                {new Date(1519377000), "1519377.000"},
                {new Date(1519000000), "1519000.000"},
                {new Date(1519377999), "1519377.999"},
                {null, "-1"},
        };
    }

    @Test(dataProvider = "convertToUnixTimestampDP")
    public void convertToUnixTimestamp(Date date, String expectedUnixTimestamp) {
        UnixTimestampConverterFunction utcf = new UnixTimestampConverterFunction();
        assertEquals(utcf.execute(Collections.singletonList(date)), expectedUnixTimestamp);
    }

}
