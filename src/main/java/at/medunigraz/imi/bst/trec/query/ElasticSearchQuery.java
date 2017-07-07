package at.medunigraz.imi.bst.trec.query;

import java.util.List;

import org.json.JSONObject;

import at.medunigraz.imi.bst.trec.model.Result;
import at.medunigraz.imi.bst.trec.model.Topic;
import at.medunigraz.imi.bst.trec.search.ElasticSearch;

public class ElasticSearchQuery implements Query {
	
	private JSONObject jsonQuery;
	
	private String index;
	
	public ElasticSearchQuery(String index) {
		this.index = index;
	}

	@Override
	public List<Result> query(Topic topic) {
		ElasticSearch es = new ElasticSearch(index);
		return es.query(jsonQuery);
	}
	
	@Override
	public void setJSONQuery(String jsonQuery) {
		this.jsonQuery = new JSONObject(jsonQuery);
	}

	@Override
	public String getJSONQuery() {
		return jsonQuery.toString();
	}

}