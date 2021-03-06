/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.oozie.executor.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.oozie.CoordinatorJobBean;
import org.apache.oozie.ErrorCode;
import org.apache.oozie.util.ParamChecker;
import org.apache.oozie.util.XLog;

/**
 * DB query executor to fetch columns 'user' and 'appName' from Coordinator Job table
 */
public class CoordinatorJobGetForUserAppnameJPAExecutor implements JPAExecutor<CoordinatorJobBean> {

    private String coordJobId = null;

    public CoordinatorJobGetForUserAppnameJPAExecutor(String coordJobId) {
        ParamChecker.notNull(coordJobId, "coordJobId");
        this.coordJobId = coordJobId;
    }

    @Override
    public String getName() {
        return "CoordinatorJobGetForUserAppnameJPAExecutor";
    }

    @Override
    public CoordinatorJobBean execute(EntityManager em) throws JPAExecutorException {
        try {
            Query q = em.createNamedQuery("GET_COORD_JOB_FOR_USER_APPNAME");
            q.setParameter("id", coordJobId);
            Object[] arr = (Object[]) q.getSingleResult();
            CoordinatorJobBean bean = new CoordinatorJobBean();
            if (arr[0] != null) {
                bean.setUser((String) arr[0]);
            }
            if (arr[1] != null){
                bean.setAppName((String) arr[1]);
            }
            return bean;
        }
        catch (Exception e) {
            throw new JPAExecutorException(ErrorCode.E0603, e.getMessage(), e);
        }
    }

}
