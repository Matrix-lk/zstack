package org.zstack.header.identity;

import org.zstack.header.message.APIMessage;
import org.zstack.header.message.APIParam;

/**
 * Created by frank on 7/9/2015.
 */
@Action(category = AccountConstant.ACTION_CATEGORY)
public class APIRemoveUserFromGroupMsg extends APIMessage implements AccountMessage {
    @APIParam(resourceType = UserVO.class)
    private String userUuid;
    @APIParam(resourceType = UserGroupVO.class)
    private String groupUuid;

    @Override
    public String getAccountUuid() {
        return getSession().getAccountUuid();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }
}
