/*
 * Sakuli - Testing and Monitoring-Tool for Websites and common UIs.
 *
 * Copyright 2013 - 2015 the original author or authors.
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

package org.sakuli.services.forwarder.database;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.sakuli.datamodel.TestCase;
import org.sakuli.datamodel.TestCaseStep;
import org.sakuli.datamodel.TestSuite;
import org.sakuli.exceptions.SakuliExceptionHandler;
import org.sakuli.exceptions.SakuliForwarderRuntimeException;
import org.sakuli.services.ResultService;
import org.sakuli.services.forwarder.AbstractTeardownService;
import org.sakuli.services.forwarder.database.dao.DaoTestCase;
import org.sakuli.services.forwarder.database.dao.DaoTestCaseStep;
import org.sakuli.services.forwarder.database.dao.DaoTestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.SortedSet;

/**
 * @author tschneck
 * Date: 09.07.14
 */
@ProfileJdbcDb
@Component
public class DatabaseResultServiceImpl extends AbstractTeardownService implements ResultService {
    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseResultServiceImpl.class);

    @Autowired
    private SakuliExceptionHandler exceptionHandler;
    @Autowired
    private DaoTestCase daoTestCase;
    @Autowired
    private DaoTestCaseStep daoTestCaseStep;
    @Autowired
    private DaoTestSuite daoTestSuite;

    @Override
    public int getServicePriority() {
        return 10;
    }

    @Override
    public void teardownTestSuite(@NonNull TestSuite testSuite) throws RuntimeException {
        LOGGER.info("======= SAVE RESULTS TO DATABASE ======");
        try {
            daoTestSuite.saveTestSuiteResult();
            daoTestSuite.saveTestSuiteToSahiJobs();

            if (!CollectionUtils.isEmpty(testSuite.getTestCases())) {
                for (TestCase tc : testSuite.getTestCasesAsSortedSet()) {
                    //write testcase and steps to DB
                    daoTestCase.saveTestCaseResult(tc);

                    LOGGER.info("... try to save all STEPS for test case '" + tc.getId() + "'!");
                    SortedSet<TestCaseStep> steps = tc.getStepsAsSortedSet();
                    if (!steps.isEmpty()) {
                        daoTestCaseStep.saveTestCaseSteps(steps, tc.getDbPrimaryKey());
                        LOGGER.info("all STEPS for '" + tc.getId() + "' saved!");
                    } else {
                        LOGGER.info("no STEPS for '\" + tc.getId() +\"'found => no STEPS saved in DB!");
                    }
                }
            }
            LOGGER.info("======= FINISHED: SAVE RESULTS TO DATABASE ======");
        } catch (Exception e) {
            throw new SakuliForwarderRuntimeException(
                    "error by saving the results to the database for: " + testSuite.toString(), e);
        }
    }

}
