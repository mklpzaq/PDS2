package com.test.pds2.resume.service;

import java.util.List;
import java.util.Map;

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
		logger.debug("ResumeDao - insertResume : "+resume.toString());
		sqlSession.insert(NS+"insertResume", resume);
		return resume.getResumeId();
	}
	
	public int insertResumeFile(ResumeFile resumeFile) {
		logger.debug("ResumeDao - insertResumeFile - resumeFile : "+resumeFile.toString());
		 
		return sqlSession.insert(NS+"insertResumeFile", resumeFile);
	}
	
	public List<Resume> selectResumeList(Map<String, Object> map) {
		logger.debug("ResumeDao - selectResumeList - resumeFile : "+map.toString());
		 
		return sqlSession.selectList(NS+"selectResumeList", map);
	}
	
	public int totalCountResume(Map<String, Object> map) {
		logger.debug("ResumeDao - totalCountResume : "+map.toString());
		 
		return sqlSession.selectOne(NS+"totalCountResume", map);
	}
}
