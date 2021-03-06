package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.Gender;
import com.foxinmy.weixin4j.qy.type.UserStatus;

/**
 * 部门成员对象
 * 
 * @className User
 * @author jy
 * @date 2014年11月19日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98">管理成员说明</a>
 */
public class User implements Serializable {

	private static final long serialVersionUID = 4747301605060801611L;
	/**
	 * 必须 员工UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字符
	 */
	@JSONField(name = "userid")
	private String userId;
	/**
	 * 必须 成员名称。长度为1~64个字符
	 */
	private String name;
	/**
	 * 非必须 成员所属部门id列表。注意，每个部门的直属员工上限为1000个
	 */
	@JSONField(name = "department")
	private List<Integer> partyIds;
	/**
	 * 非必须 职位信息。长度为0~64个字符
	 */
	private String position;
	/**
	 * 非必须 手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
	 */
	private String mobile;
	/**
	 * 非必须 性别。gender=0表示男，=1表示女。默认gender=0
	 */
	private Integer gender;
	/**
	 * 非必须 办公电话。长度为0~64个字符
	 */
	private String tel;
	/**
	 * 非必须 邮箱。长度为0~64个字符。企业内必须唯一
	 */
	private String email;
	/**
	 * 非必须 微信号。企业内必须唯一
	 */
	@JSONField(name = "weixinid")
	private String weixinId;
	/**
	 * 头像url。注：如果要获取小图将url最后的"/0"改成"/64"即可
	 */
	private String avatar;
	/**
	 * 关注状态: 1=已关注，2=已冻结，4=未关注
	 */
	private Integer status;
	/**
	 * 启用/禁用成员。1表示启用成员，0表示禁用成员
	 */
	private Integer enable;
	/**
	 * 非必须 扩展属性。扩展属性需要在WEB管理端创建后才生效，否则忽略未知属性的赋值
	 */
	private List<NameValue> extattr;

	protected User() {
	}

	public User(String userId, String name) {
		this.userId = userId;
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public List<Integer> getPartyIds() {
		return partyIds;
	}

	public void setPartyIds(Integer... partyIds) {
		this.partyIds = Arrays.asList(partyIds);
	}

	public String getPosition() {
		return position;
	}

	public String getMobile() {
		return mobile;
	}

	public Integer getGender() {
		return gender;
	}

	@JSONField(serialize = false)
	public Gender getFormatGender() {
		if (gender != null) {
			if (gender.intValue() == 0) {
				return Gender.male;
			} else if (gender.intValue() == 1) {
				return Gender.female;
			} else {
				return Gender.unknown;
			}
		}
		return null;
	}

	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public String getAvatar() {
		return avatar;
	}

	@JSONField(serialize = false)
	public UserStatus getFormatStatus() {
		if (status != null) {
			for (UserStatus userStatus : UserStatus.values()) {
				if (userStatus.getVal() == status) {
					return userStatus;
				}
			}
		}
		return null;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable ? 1 : 0;
	}

	@JSONField(serialize = false)
	public boolean getFormatEnable() {
		if (enable != null) {
			return enable.intValue() == 1;
		}
		return false;
	}

	public List<NameValue> getExtattr() {
		return extattr;
	}

	public void setExtattr(List<NameValue> extattr) {
		this.extattr = extattr;
	}

	public void setExtattr(NameValue... extattr) {
		this.extattr = Arrays.asList(extattr);
	}

	public void pushExattr(String name, String value) {
		extattr.add(new NameValue(name, value));
	}

	// ---------- setter 应该全部去掉

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPartyIds(List<Integer> partyIds) {
		this.partyIds = partyIds;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", partyIds="
				+ partyIds + ", position=" + position + ", mobile=" + mobile
				+ ", gender=" + gender + ", tel=" + tel + ", email=" + email
				+ ", weixinId=" + weixinId + ", avatar=" + avatar + ", status="
				+ status + ", enable=" + enable + ", extattr=" + extattr + "]";
	}
}
