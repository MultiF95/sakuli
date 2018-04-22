/*
 * Sakuli - Testing and Monitoring-Tool for Websites and common UIs.
 *
 * Copyright 2013 - 2017 the original author or authors.
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

package org.sakuli.starter.sahi.loader;

import net.sf.sahi.report.Report;
import net.sf.sahi.rhino.RhinoScriptRunner;
import org.sakuli.loader.BaseActionLoader;
import org.sakuli.starter.sahi.datamodel.properties.SahiProxyProperties;

/**
 * @author tschneck
 *         Date: 4/19/17
 */
public interface SahiActionLoader {
    SahiProxyProperties getSahiProxyProperties();

    RhinoScriptRunner getRhinoScriptRunner();

    void setRhinoScriptRunner(RhinoScriptRunner rhinoScriptRunner);

    Report getSahiReport();

    BaseActionLoader getBaseActionLoader();
}
