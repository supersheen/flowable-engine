/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.cmmn.engine.impl.persistence.entity.data.impl;

import java.util.List;

import org.flowable.cmmn.engine.CmmnEngineConfiguration;
import org.flowable.cmmn.engine.impl.persistence.entity.PlanItemInstanceEntity;
import org.flowable.cmmn.engine.impl.persistence.entity.PlanItemInstanceEntityImpl;
import org.flowable.cmmn.engine.impl.persistence.entity.data.AbstractCmmnDataManager;
import org.flowable.cmmn.engine.impl.persistence.entity.data.PlanItemInstanceDataManager;
import org.flowable.cmmn.engine.impl.runtime.PlanItemInstanceQueryImpl;
import org.flowable.cmmn.engine.runtime.PlanItemInstance;

/**
 * @author Joram Barrez
 */
public class MybatisPlanItemInstanceDataManagerImpl extends AbstractCmmnDataManager<PlanItemInstanceEntity> implements PlanItemInstanceDataManager {

    public MybatisPlanItemInstanceDataManagerImpl(CmmnEngineConfiguration cmmnEngineConfiguration) {
        super(cmmnEngineConfiguration);
    }

    @Override
    public Class<? extends PlanItemInstanceEntity> getManagedEntityClass() {
        return PlanItemInstanceEntityImpl.class;
    }

    @Override
    public PlanItemInstanceEntity create() {
        return new PlanItemInstanceEntityImpl();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PlanItemInstanceEntity> findChildPlanItemInstancesForStage(String stagePlanItemInstanceId) {
        return getDbSqlSession().selectList("selectChildPlanItemInstancesForStage", stagePlanItemInstanceId);
    }
    
    @Override
    public PlanItemInstanceEntity findPlanModelPlanItemInstanceForCaseInstance(String caseInstanceId) {
        return (PlanItemInstanceEntity) getDbSqlSession().selectOne("selectPlanModelPlanItemInstanceForCaseInstance", caseInstanceId);
    }

    @Override
    public long countByCriteria(PlanItemInstanceQueryImpl planItemInstanceQuery) {
        return (Long) getDbSqlSession().selectOne("selectPlanItemInstanceCountByQueryCriteria", planItemInstanceQuery);
    }

    @Override
    public List<PlanItemInstance> findByCriteria(PlanItemInstanceQueryImpl planItemInstanceQuery) {
        return getDbSqlSession().selectList("selectPlanItemInstancesByQueryCriteria", planItemInstanceQuery);
    }
    
    @Override
    public void deleteByCaseDefinitionId(String caseDefinitionId) {
        getDbSqlSession().delete("deletePlanItemInstanceByCaseDefinitionId", caseDefinitionId, getManagedEntityClass());
    }
    
}