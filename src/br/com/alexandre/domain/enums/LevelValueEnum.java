package br.com.alexandre.domain.enums;

public enum LevelValueEnum {

	LEVEL_ONE(10000),
	LEVEL_TWO(1000),
	LEVEL_THREE(100),
	LEVEL_FOUR(10),
	LEVEL_FIVE(1);
	
	private Integer value;
	
	LevelValueEnum(Integer value){
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	
}
