package com.bol.core.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "job_title")
public class JobTitles {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;
		private String title;
		private String created;
		private String createdBy;
		private String updated ;
		private String updatedBy;
	    private Boolean active;
	    private String template;
	    
	    
		public JobTitles() {
			super();
			// TODO Auto-generated constructor stub
		}



		public JobTitles(long id, String title, String created, String createdBy, String updated, String updatedBy,
				Boolean active, String template) {
			super();
			this.id = id;
			this.title = title;
			this.created = created;
			this.createdBy = createdBy;
			this.updated = updated;
			this.updatedBy = updatedBy;
			this.active = active;
			this.template = template;
		}



		public long getId() {
			return id;
		}



		public void setId(long id) {
			this.id = id;
		}



		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
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


		public String getTemplate() {
			return template;
		}


		public void setTemplate(String template) {
			this.template = template;
		}


		

		
	
	
	 
	 

}
