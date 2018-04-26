package com.test.pds2.resume.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResumeFileDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	final String NS ="com.test.pds2.resume.service.ResumeFileMapper.";
	private static final Logger logger = LoggerFactory.getLogger(ResumeFileDao.class);
	
	
	public int insertResumeFile(ResumeFile resumeFile) {
		logger.debug("insertResumeFile - resumeFile : "+resumeFile.toString());
		 
		return sqlSession.insert(NS+"insertResumeFile", resumeFile);  
	}
	
	public int deleteFileResume(Resume resume) {
		logger.debug("deleteFileResume - resume : "+resume.toString());
		 
		return sqlSession.delete(NS+"deleteFileResume", resume);  
	}
}