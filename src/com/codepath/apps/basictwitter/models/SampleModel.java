package com.codepath.apps.basictwitter.models;


/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the ActiveAndroid wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
@Table(name = "items")
public class SampleModel extends Model {
	// Define table fields
	@Column(name = "name")
	private String name;
	
	public SampleModel() {
		super();
	}
	
	// Parse model from JSON
	public SampleModel(JSONObject object){
		super();

		try {
			this.name = object.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	// Record Finders
	public static SampleModel byId(long id) {
	   return new Select().from(SampleModel.class).where("id = ?", id).executeSingle();
	}
	
	public static List<SampleModel> recentItems() {
      return new Select().from(SampleModel.class).orderBy("id DESC").limit("300").execute();
	}
}
