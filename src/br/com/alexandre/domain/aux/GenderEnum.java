package br.com.alexandre.domain.aux;

public enum GenderEnum {

	MASCULINO("M"),
	FEMININO("F"),
	INDEFINIDO("I");
	
	private String value;
	
	GenderEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
