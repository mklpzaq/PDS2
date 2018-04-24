package com.test.pds2.resume.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ResumeDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	final String NS ="com.test.pds2.resume.service.ResumeMapper.";		
	private static final Logger logger = LoggerFactory.getLogger(ResumeDao.class);
	
	public int insertResume(Resume resume) {
		logger.info("ResumeDao - insertResume : "+resume.toString());
		 
		return sqlSession.insert(NS+"insertResume", resume);
	}
	
	public int insertResumeFile(ResumeFile resumeFile) {
		logger.info("ResumeDao - insertResumeFile : "+resumeFile.toString());
		 
		return sqlSession.insert(NS+"insertResumeFile", resumeFile);
	}
}
