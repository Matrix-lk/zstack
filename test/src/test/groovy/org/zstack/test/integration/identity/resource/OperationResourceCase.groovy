package org.zstack.test.integration.identity.resource

import org.zstack.core.db.DatabaseFacade
import org.zstack.header.identity.AccountConstant
import org.zstack.header.vm.VmInstanceState
import org.zstack.header.vm.VmInstanceVO
import org.zstack.sdk.AccountInventory
import org.zstack.sdk.InstanceOfferingInventory
import org.zstack.sdk.SessionInventory
import org.zstack.sdk.VmInstanceInventory
import org.zstack.test.integration.ZStackTest
import org.zstack.test.integration.identity.Env
import org.zstack.testlib.EnvSpec
import org.zstack.testlib.SubCase

/**
 * Created by camile on 2017/6/11.
 */
class OperationResourceCase extends SubCase {
    EnvSpec envSpec
    AccountInventory accountInventory

    @Override
    void clean() {
        envSpec.delete()
    }

    @Override
    void setup() {
        useSpring(ZStackTest.springSpec)
    }

    @Override
    void environment() {
        envSpec = Env.oneVmBasicEnv()
    }

    void testChangeOwnerWhenVMStarting() {
        DatabaseFacade dbf = bean(DatabaseFacade.class)
        VmInstanceInventory vm = envSpec.inventoryByName("vm")
        accountInventory = createAccount {
            name = "test"
            password = "password"
        }
        VmInstanceVO vmVo = dbf.findByUuid(vm.uuid, VmInstanceVO.class)
        vmVo.setState(VmInstanceState.Starting)
        dbf.update(vmVo)
        changeResourceOwner{
            accountUuid = accountInventory.uuid
            resourceUuid = vmVo.uuid
        }
    }

    void testShareResourceAgain(){
        logInByAccount {
            accountName = AccountConstant.INITIAL_SYSTEM_ADMIN_NAME
            password = AccountConstant.INITIAL_SYSTEM_ADMIN_PASSWORD
        }

        InstanceOfferingInventory instanceOfferingInventory = envSpec.inventoryByName("instanceOffering")
        shareResource {
            resourceUuids = [instanceOfferingInventory.uuid]
            accountUuids = [accountInventory.uuid]
        }

        shareResource {
            resourceUuids = [instanceOfferingInventory.uuid]
            accountUuids = [accountInventory.uuid]
        }
        revokeResourceSharing{
            resourceUuids = [instanceOfferingInventory.uuid]
            accountUuids = [accountInventory.uuid]
        }
    }

    @Override
    void test() {
        envSpec.create {
            testChangeOwnerWhenVMStarting()
            testShareResourceAgain()
        }
    }
}
