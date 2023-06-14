package com.example.soap.webservices.soapcoursemanagement.soap;


import com.example.courses.CourseDetails;
import com.example.courses.GetAllCourseDetailsRequest;
import com.example.courses.GetAllCourseDetailsResponse;
import com.example.courses.GetCourseDetailsRequest;
import com.example.courses.GetCourseDetailsResponse;
import com.example.soap.webservices.soapcoursemanagement.soap.bean.Course;
import com.example.soap.webservices.soapcoursemanagement.soap.service.CourseDetailsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CourseDetailsEndpoint {
	
	@Autowired
	CourseDetailsService service;

	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse

	// http://example.com/courses
	// GetCourseDetailsRequest
	@PayloadRoot(namespace = "http://example.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

		Course course = service.findById(request.getId());
		
		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		
		response.setCourseDetails(mapCourse(course));

		return response;
	}
	
	@PayloadRoot(namespace = "http://example.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {
		//Course course = service.findById(request.getId());
		List<Course> courses = service.findAll();
		return mapAllCourseDetails(courses);
	}
	
	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		
		for(Course course:courses)
		{
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		
		courseDetails.setId(course.getId());
		
		courseDetails.setName(course.getName());
		
		courseDetails.setDescription(course.getDescription());
		
		return courseDetails;
	}
}