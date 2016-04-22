package works.Attendance;

public class RowItemAll {
	
	    private String date;
		private String topic;
		private String periods;
	    private String people;
	    private String present;
	    private String abscent;
	    private String percent;
	    
	    public RowItemAll(String date, String topic, String periods, String people, String present, String absent, String percent) {
	        this.date = date;
	        this.topic = topic;
	        this.periods = periods;
	        this.people = people;
	        this.present = present;
	        this.abscent = absent;
	        this.percent = percent;

	    }
	    public String getDate() {
	        return date;
	    }
	    public void setDate(String date) {
	        this.date = date;
	    }
	    public String getTopic() {
	        return topic;
	    }
	    public void setTopic(String topic) {
	        this.topic = topic;
	    }
	    public String getPeriods() {
	        return periods;
	    }
	    public void setPeriods(String periods) {
	        this.periods = periods;
	    }
	    
	    public String getPeople() {
	        return people;
	    }
	    public void setPeople(String people) {
	        this.people = people;
	    }
	    
	    public String getPresent() {
	        return present;
	    }
	    public void setPresent(String present) {
	        this.present = present;
	    }
	    
	    public String getAbscent() {
	        return abscent;
	    }
	    public void setAbscent(String abscent) {
	        this.abscent = abscent;
	    }
	    
	    public String getPercent() {
	        return percent;
	    }
	    public void setPercent(String percent) {
	        this.percent = percent;
	    }
	    
	    @Override
	    public String toString() {
	        return percent;
	    }

}
