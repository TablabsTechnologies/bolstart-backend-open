package com.bol.core.dto;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;

@Entity
@Table(name = "job_post")
public class JobPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long companyId; // Companyid : Reference --> Comapany,
	private long recruiter; // Recruter : Reference --> user table,
	private long jobtitleId; // Job Title : Reference --> Job titles

	private String typeofJob; // (Intership , Full time , part time, Freelancer),
	private String JobDescription;
	private String responsiblitie; // (comma seperated),
	private String minExperiance;
	private String maxExperiance;
	private String benifit; // (comma seperated),
	private String startDate;
	private String duration;
	private String salaryMin;
	private String salaryMax;
	private String location;
	private String country;
	private String skills;
	private String noOfApplication;
	// private List<Skills> skill;

	private String noticePeriod;
	private String city;
	private String state;
	private String created;
	private String createdBy;
	private String updated;
	private String updatedBy;
	private Boolean active;
	private String jobCategeory;
	private String qualification;
	private String requirements;
	private String bond;
	private Boolean isResumeRequired;
	private Boolean isAssissmentRequired;
	private String assissmentId;
	private Boolean status;
	
	private String image;

	public JobPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	

//	public JobPost(long id, long companyId, long recruiter, long jobtitleId, String typeofJob, String jobDescription,
//			String responsiblitie, String minExperiance, String maxExperiance, String benifit, String startDate,
//			String duration, String salaryMin, String salaryMax, String location, String country, String skills,
//			String noticePeriod, String city, String state, String created, String createdBy, String updated,
//			String updatedBy, Boolean active, String jobCategeory, String qualification, String requirements,
//			String bond, Boolean isResumeRequired, Boolean isAssissmentRequired, String assissmentId, String image) {
//		super();
//		this.id = id;
//		this.companyId = companyId;
//		this.recruiter = recruiter;
//		this.jobtitleId = jobtitleId;
//		this.typeofJob = typeofJob;
//		JobDescription = jobDescription;
//		this.responsiblitie = responsiblitie;
//		this.minExperiance = minExperiance;
//		this.maxExperiance = maxExperiance;
//		this.benifit = benifit;
//		this.startDate = startDate;
//		this.duration = duration;
//		this.salaryMin = salaryMin;
//		this.salaryMax = salaryMax;
//		this.location = location;
//		this.country = country;
//		this.skills = skills;
//		this.noticePeriod = noticePeriod;
//		this.city = city;
//		this.state = state;
//		this.created = created;
//		this.createdBy = createdBy;
//		this.updated = updated;
//		this.updatedBy = updatedBy;
//		this.active = active;
//		this.jobCategeory = jobCategeory;
//		this.qualification = qualification;
//		this.requirements = requirements;
//		this.bond = bond;
//		this.isResumeRequired = isResumeRequired;
//		this.isAssissmentRequired = isAssissmentRequired;
//		this.assissmentId = assissmentId;
//		this.image = image;
//	}
	
	
	

	public JobPost(long id, long companyId, long recruiter, long jobtitleId, String typeofJob, String jobDescription,
			String responsiblitie, String minExperiance, String maxExperiance, String benifit, String startDate,
			String duration, String salaryMin, String salaryMax, String location, String country, String skills,
			String noOfApplication, String noticePeriod, String city, String state, String created, String createdBy,
			String updated, String updatedBy, Boolean active, String jobCategeory, String qualification,
			String requirements, String bond, Boolean isResumeRequired, Boolean isAssissmentRequired,
			String assissmentId, Boolean status, String image, Company company, JobTitles jobTitle, Users users) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.recruiter = recruiter;
		this.jobtitleId = jobtitleId;
		this.typeofJob = typeofJob;
		JobDescription = jobDescription;
		this.responsiblitie = responsiblitie;
		this.minExperiance = minExperiance;
		this.maxExperiance = maxExperiance;
		this.benifit = benifit;
		this.startDate = startDate;
		this.duration = duration;
		this.salaryMin = salaryMin;
		this.salaryMax = salaryMax;
		this.location = location;
		this.country = country;
		this.skills = skills;
		this.noOfApplication = noOfApplication;
		this.noticePeriod = noticePeriod;
		this.city = city;
		this.state = state;
		this.created = created;
		this.createdBy = createdBy;
		this.updated = updated;
		this.updatedBy = updatedBy;
		this.active = active;
		this.jobCategeory = jobCategeory;
		this.qualification = qualification;
		this.requirements = requirements;
		this.bond = bond;
		this.isResumeRequired = isResumeRequired;
		this.isAssissmentRequired = isAssissmentRequired;
		this.assissmentId = assissmentId;
		this.status = status;
		this.image = image;
		this.company = company;
		JobTitle = jobTitle;
		this.users = users;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(long recruiter) {
		this.recruiter = recruiter;
	}

	public long getJobtitleId() {
		return jobtitleId;
	}

	public void setJobtitleId(long jobtitleId) {
		this.jobtitleId = jobtitleId;
	}

	public String getTypeofJob() {
		return typeofJob;
	}

	public void setTypeofJob(String typeofJob) {
		this.typeofJob = typeofJob;
	}

	public String getJobDescription() {
		return JobDescription;
	}

	public void setJobDescription(String jobDescription) {
		JobDescription = jobDescription;
	}

	public String getResponsiblitie() {
		return responsiblitie;
	}

	public void setResponsiblitie(String responsiblitie) {
		this.responsiblitie = responsiblitie;
	}

	public String getMinExperiance() {
		return minExperiance;
	}

	public void setMinExperiance(String minExperiance) {
		this.minExperiance = minExperiance;
	}

	public String getMaxExperiance() {
		return maxExperiance;
	}

	public void setMaxExperiance(String maxExperiance) {
		this.maxExperiance = maxExperiance;
	}

	public String getBenifit() {
		return benifit;
	}

	public void setBenifit(String benifit) {
		this.benifit = benifit;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSalaryMin() {
		return salaryMin;
	}

	public void setSalaryMin(String salaryMin) {
		this.salaryMin = salaryMin;
	}

	public String getSalaryMax() {
		return salaryMax;
	}

	public void setSalaryMax(String salaryMax) {
		this.salaryMax = salaryMax;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getJobCategeory() {
		return jobCategeory;
	}

	public void setJobCategeory(String jobCategeory) {
		this.jobCategeory = jobCategeory;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public String getBond() {
		return bond;
	}

	public void setBond(String bond) {
		this.bond = bond;
	}

	public Boolean getIsResumeRequired() {
		return isResumeRequired;
	}

	public void setIsResumeRequired(Boolean isResumeRequired) {
		this.isResumeRequired = isResumeRequired;
	}

	public Boolean getIsAssissmentRequired() {
		return isAssissmentRequired;
	}

	public void setIsAssissmentRequired(Boolean isAssissmentRequired) {
		this.isAssissmentRequired = isAssissmentRequired;
	}

	public String getAssissmentId() {
		return assissmentId;
	}

	public void setAssissmentId(String assissmentId) {
		this.assissmentId = assissmentId;
	}

	@Transient
	Company company;

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	@Transient
	JobTitles JobTitle;

	public JobTitles getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(JobTitles jobTitle) {
		JobTitle = jobTitle;
	}
	
	

	public String getNoOfApplication() {
		return noOfApplication;
	}

	public void setNoOfApplication(String noOfApplication) {
		this.noOfApplication = noOfApplication;
	}





	public Boolean getStatus() {
		return status;
	}



	public void setStatus(Boolean status) {
		this.status = status;
	}







	@Override
	public String toString() {
		return "JobPost [id=" + id + ", companyId=" + companyId + ", recruiter=" + recruiter + ", jobtitleId="
				+ jobtitleId + ", typeofJob=" + typeofJob + ", JobDescription=" + JobDescription + ", responsiblitie="
				+ responsiblitie + ", minExperiance=" + minExperiance + ", maxExperiance=" + maxExperiance
				+ ", benifit=" + benifit + ", startDate=" + startDate + ", duration=" + duration + ", salaryMin="
				+ salaryMin + ", salaryMax=" + salaryMax + ", location=" + location + ", country=" + country
				+ ", skills=" + skills + ", noticePeriod=" + noticePeriod + ", city=" + city + ", state=" + state
				+ ", created=" + created + ", createdBy=" + createdBy + ", updated=" + updated + ", updatedBy="
				+ updatedBy + ", active=" + active + ", jobCategeory=" + jobCategeory + ", qualification="
				+ qualification + ", requirements=" + requirements + ", bond=" + bond + ", isResumeRequired="
				+ isResumeRequired + ", isAssissmentRequired=" + isAssissmentRequired + ", assissmentId=" + assissmentId
				+ ", company=" + company + ", JobTitle=" + JobTitle + "]";
	}

	@Transient
	Users users;

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(JobDescription, active, assissmentId, benifit, bond, city, companyId, country, created,
				createdBy, duration, id, isAssissmentRequired, isResumeRequired, jobCategeory, jobtitleId, location,
				maxExperiance, minExperiance, noticePeriod, qualification, recruiter, requirements, responsiblitie,
				salaryMax, salaryMin, skills, startDate, state, typeofJob, updated, updatedBy);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobPost other = (JobPost) obj;
		return Objects.equals(JobDescription, other.JobDescription) && Objects.equals(active, other.active)
				&& Objects.equals(assissmentId, other.assissmentId) && Objects.equals(benifit, other.benifit)
				&& Objects.equals(bond, other.bond) && Objects.equals(city, other.city) && companyId == other.companyId
				&& Objects.equals(country, other.country) && Objects.equals(created, other.created)
				&& Objects.equals(createdBy, other.createdBy) && Objects.equals(duration, other.duration)
				&& id == other.id && Objects.equals(isAssissmentRequired, other.isAssissmentRequired)
				&& Objects.equals(isResumeRequired, other.isResumeRequired)
				&& Objects.equals(jobCategeory, other.jobCategeory) && jobtitleId == other.jobtitleId
				&& Objects.equals(location, other.location) && Objects.equals(maxExperiance, other.maxExperiance)
				&& Objects.equals(minExperiance, other.minExperiance)
				&& Objects.equals(noticePeriod, other.noticePeriod)
				&& Objects.equals(qualification, other.qualification) && recruiter == other.recruiter
				&& Objects.equals(requirements, other.requirements)
				&& Objects.equals(responsiblitie, other.responsiblitie) && Objects.equals(salaryMax, other.salaryMax)
				&& Objects.equals(salaryMin, other.salaryMin) && Objects.equals(skills, other.skills)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(state, other.state)
				&& Objects.equals(typeofJob, other.typeofJob) && Objects.equals(updated, other.updated)
				&& Objects.equals(updatedBy, other.updatedBy);
	}

	
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	
	

}
