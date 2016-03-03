package org.rcsb.mmtf.gitversion;

import java.util.Properties;

/**
 * Class to store the current git information
 * @author abradley
 *
 */
public class GitRepositoryState {

	  private String tags;                    // =${git.tags} // comma separated tag names
	  private  String branch;                  // =${git.branch}
	  private String dirty;                   // =${git.dirty}
	  private String remoteOriginUrl;         // =${git.remote.origin.url}

	  private String commitId;                // =${git.commit.id.full} OR ${git.commit.id}
	  private String commitIdAbbrev;          // =${git.commit.id.abbrev}
	  private String describe;                // =${git.commit.id.describe}
	  private String describeShort;           // =${git.commit.id.describe-short}
	  private String commitUserName;          // =${git.commit.user.name}
	  private String commitUserEmail;         // =${git.commit.user.email}
	  private String commitMessageFull;       // =${git.commit.message.full}
	  private String commitMessageShort;      // =${git.commit.message.short}
	  private String commitTime;              // =${git.commit.time}
	  private String closestTagName;          // =${git.closest.tag.name}
	  private String closestTagCommitCount;   // =${git.closest.tag.commit.count}

	  private String buildUserName;           // =${git.build.user.name}
	  private String buildUserEmail;          // =${git.build.user.email}
	  private String buildTime;               // =${git.build.time}
	  private String buildHost;               // =${git.build.host}
	  private String buildVersion;             // =${git.build.version}
	  
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getDirty() {
		return dirty;
	}

	public void setDirty(String dirty) {
		this.dirty = dirty;
	}

	public String getRemoteOriginUrl() {
		return remoteOriginUrl;
	}

	public void setRemoteOriginUrl(String remoteOriginUrl) {
		this.remoteOriginUrl = remoteOriginUrl;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public String getCommitIdAbbrev() {
		return commitIdAbbrev;
	}

	public void setCommitIdAbbrev(String commitIdAbbrev) {
		this.commitIdAbbrev = commitIdAbbrev;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getDescribeShort() {
		return describeShort;
	}

	public void setDescribeShort(String describeShort) {
		this.describeShort = describeShort;
	}

	public String getCommitUserName() {
		return commitUserName;
	}

	public void setCommitUserName(String commitUserName) {
		this.commitUserName = commitUserName;
	}

	public String getCommitUserEmail() {
		return commitUserEmail;
	}

	public void setCommitUserEmail(String commitUserEmail) {
		this.commitUserEmail = commitUserEmail;
	}

	public String getCommitMessageFull() {
		return commitMessageFull;
	}

	public void setCommitMessageFull(String commitMessageFull) {
		this.commitMessageFull = commitMessageFull;
	}

	public String getCommitMessageShort() {
		return commitMessageShort;
	}

	public void setCommitMessageShort(String commitMessageShort) {
		this.commitMessageShort = commitMessageShort;
	}

	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

	public String getClosestTagName() {
		return closestTagName;
	}

	public void setClosestTagName(String closestTagName) {
		this.closestTagName = closestTagName;
	}

	public String getClosestTagCommitCount() {
		return closestTagCommitCount;
	}

	public void setClosestTagCommitCount(String closestTagCommitCount) {
		this.closestTagCommitCount = closestTagCommitCount;
	}

	public String getBuildUserName() {
		return buildUserName;
	}

	public void setBuildUserName(String buildUserName) {
		this.buildUserName = buildUserName;
	}

	public String getBuildUserEmail() {
		return buildUserEmail;
	}

	public void setBuildUserEmail(String buildUserEmail) {
		this.buildUserEmail = buildUserEmail;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public String getBuildHost() {
		return buildHost;
	}

	public void setBuildHost(String buildHost) {
		this.buildHost = buildHost;
	}

	public String getBuildVersion() {
		return buildVersion;
	}

	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}

	public GitRepositoryState(Properties properties)
	{
		
	  this.tags = String.valueOf(properties.get("git.tags"));
	  this.branch = String.valueOf(properties.get("git.branch"));
	  this.dirty = String.valueOf(properties.get("git.dirty"));
	  this.remoteOriginUrl = String.valueOf(properties.get("git.remote.origin.url"));

	  this.commitId = String.valueOf(properties.get("git.commit.id")); // OR properties.get("git.commit.id") depending on your configuration
	  this.commitIdAbbrev = String.valueOf(properties.get("git.commit.id.abbrev"));
	  this.describe = String.valueOf(properties.get("git.commit.id.describe"));
	  this.describeShort = String.valueOf(properties.get("git.commit.id.describe-short"));
	  this.commitUserName = String.valueOf(properties.get("git.commit.user.name"));
	  this.commitUserEmail = String.valueOf(properties.get("git.commit.user.email"));
	  this.commitMessageFull = String.valueOf(properties.get("git.commit.message.full"));
	  this.commitMessageShort = String.valueOf(properties.get("git.commit.message.short"));
	  this.commitTime = String.valueOf(properties.get("git.commit.time"));
	  this.closestTagName = String.valueOf(properties.get("git.closest.tag.name"));
	  this.closestTagCommitCount = String.valueOf(properties.get("git.closest.tag.commit.count"));

	  this.buildUserName = String.valueOf(properties.get("git.build.user.name"));
	  this.buildUserEmail = String.valueOf(properties.get("git.build.user.email"));
	  this.buildTime = String.valueOf(properties.get("git.build.time"));
	  this.buildHost = String.valueOf(properties.get("git.build.host"));
	  this.buildVersion = String.valueOf(properties.get("git.build.version"));
	}
}
