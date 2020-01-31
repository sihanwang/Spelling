package com.sihanwang.study.spelling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonRootName("YoudaoDictResponse")
	
public class YoudaoDictResponse {

	private String tSpeakUrl;
	private String[] returnPhrase;
	
	@JsonIgnore
	private String[] web;
	private String query;
	private String[] translation;
	private String errorCode;
	
	@JsonIgnore
	private String dict;
	@JsonIgnore
	private String webdict;
	private Basic basic;
	private String l;
	private String speakUrl;
	
	public YoudaoDictResponse() {
		super();
	}
	
	public YoudaoDictResponse(String tSpeakUrl, String[] returnPhrase, String[] web, String query, String[] translation,
			String errorCode, String dict, String webdict, Basic basic, String l, String speakUrl) {
		super();
		this.tSpeakUrl = tSpeakUrl;
		this.returnPhrase = returnPhrase;
		this.web = web;
		this.query = query;
		this.translation = translation;
		this.errorCode = errorCode;
		this.dict = dict;
		this.webdict = webdict;
		this.basic = basic;
		this.l = l;
		this.speakUrl = speakUrl;
	}
	
	public String gettSpeakUrl() {
		return tSpeakUrl;
	}
	public void settSpeakUrl(String tSpeakUrl) {
		this.tSpeakUrl = tSpeakUrl;
	}
	public String[] getReturnPhrase() {
		return returnPhrase;
	}
	public void setReturnPhrase(String[] returnPhrase) {
		this.returnPhrase = returnPhrase;
	}
	public String[] getWeb() {
		return web;
	}
	public void setWeb(String[] web) {
		this.web = web;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String[] getTranslation() {
		return translation;
	}
	public void setTranslation(String[] translation) {
		this.translation = translation;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getDict() {
		return dict;
	}
	public void setDict(String dict) {
		this.dict = dict;
	}
	public String getWebdict() {
		return webdict;
	}
	public void setWebdict(String webdict) {
		this.webdict = webdict;
	}
	public Basic getBasic() {
		return basic;
	}
	public void setBasic(Basic basic) {
		this.basic = basic;
	}
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l = l;
	}
	public String getSpeakUrl() {
		return speakUrl;
	}
	public void setSpeakUrl(String speakUrl) {
		this.speakUrl = speakUrl;
	}
	
}

@JsonRootName("basic")
class Basic
{
	public String[] exam_type;
	
	@JsonProperty("us-phonetic")
	public String usphonetic;
	
	public String phonetic;
	
	@JsonProperty("uk-phonetic")
	public String ukphonetic;
	
	@JsonProperty("uk-speech")
	public String ukspeech;
	
	public String[] explains;
	
	@JsonProperty("us-speech")
	public String usspeech;
	
	public String[] getExam_type() {
		return exam_type;
	}

	public void setExam_type(String[] exam_type) {
		this.exam_type = exam_type;
	}

	public String getUsphonetic() {
		return usphonetic;
	}

	public void setUsphonetic(String usphonetic) {
		this.usphonetic = usphonetic;
	}

	public String getPhonetic() {
		return phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public String getUkphonetic() {
		return ukphonetic;
	}

	public void setUkphonetic(String ukphonetic) {
		this.ukphonetic = ukphonetic;
	}

	public String getUkspeech() {
		return ukspeech;
	}

	public void setUkspeech(String ukspeech) {
		this.ukspeech = ukspeech;
	}

	public String[] getExplains() {
		return explains;
	}

	public void setExplains(String[] explains) {
		this.explains = explains;
	}

	public String getUsspeech() {
		return usspeech;
	}

	public void setUsspeech(String usspeech) {
		this.usspeech = usspeech;
	}
}
