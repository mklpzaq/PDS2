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
	//servlet-context.xml에서 미리 준비해둔 SqlSessionTemplate을 호출하여 변수 sqlSession에 담아 주입시킨다.
	final String NS ="com.test.pds2.resume.service.ResumeMapper.";
	//mapper의 namespace 미리 상수에 담아 준비해둔다. 
	private static final Logger logger = LoggerFactory.getLogger(ResumeDao.class);
	
	public int insertResume(Resume resume) {
		logger.debug("insertResume : "+resume.toString());
		
		sqlSession.insert(NS+"insertResume", resume);
		//주입시켜두었던 sqlSession에 접근해 맵핑된 도메인과 ResumeService에서 넘어온 Resume타입 resume변수를 입력값으로 넣고 insert메서드를 호출한다
		
		return resume.getResumeId();
		//ResumeMapper에서 쿼리 실행후 resume객체에 자동 셋팅된 ResumeId자동증가값을 겟팅해서 리턴
	}	
		
	public List<Resume> selectResumeList(Map<String, Object> map) {
		logger.debug("selectResumeList - resumeFile : "+map.toString());
		 
		return sqlSession.selectList(NS+"selectResumeList", map);
	}
	
	public int totalCountResume(Map<String, Object> map) {
		logger.debug("totalCountResume - map : "+map.toString());
		 
		return sqlSession.selectOne(NS+"totalCountResume", map);
	}
	
	public Resume resumeView(Resume resume) {
		logger.debug("resumeView - resume : "+resume.toString());
		
		return sqlSession.selectOne(NS+"selectResumeOne", resume);
	}
	
	public int deleteResume(Resume resume) {
		logger.debug("deleteResume - resume : "+resume.toString());
		 
		return sqlSession.delete(NS+"deleteResume", resume);  
	}
	
	public Resume updateResume(Resume resume) {
		logger.debug("updateResume - resume : "+resume.toString());
		
		return sqlSession.selectOne(NS+"selectResumeOne", resume);
	}
}
