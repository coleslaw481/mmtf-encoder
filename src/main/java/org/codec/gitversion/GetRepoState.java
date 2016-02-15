package org.codec.gitversion;

import java.io.IOException;
import java.util.Properties;

public class GetRepoState {


	public GitRepositoryState getGitRepositoryState() throws IOException
	{
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("git.properties"));

		GitRepositoryState gitRepositoryState = new GitRepositoryState(properties);

		return gitRepositoryState;
	}
	
	public String getCurrentVersion() throws IOException {
		// TODO Auto-generated method stub	
		GetRepoState grs = new GetRepoState();
		GitRepositoryState repoState = grs.getGitRepositoryState();
		return repoState.getCommitId();
	}
}
