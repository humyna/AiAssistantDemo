package info.zoio.langchain4jside.extend.langchain.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
/**
* AI助手支持业务场景枚举类
 *
* @author humyna
* @date 2025/1/21 17:46
*/
public enum AiBusinessTypeEnum {
    USER("用户"),
    PRODUCT("产品"),
    OTHER("其他")
    ;

    private final String keyword;

    public String getKeyword() {
        return keyword;
    }

    AiBusinessTypeEnum(String keyword){
        this.keyword = keyword;
    }

    public static AiBusinessTypeEnum getByName(String name){
        Map<String, AiBusinessTypeEnum> enumMap = Arrays.stream(AiBusinessTypeEnum.values())
                .collect(Collectors.toMap(AiBusinessTypeEnum::name, e -> e));
        return enumMap.get(name);
    }

    public static AiBusinessTypeEnum getByKeyword(String keyword){
        Map<String, AiBusinessTypeEnum> enumMap = Arrays.stream(AiBusinessTypeEnum.values())
                .collect(Collectors.toMap(AiBusinessTypeEnum::getKeyword, e -> e));
        return enumMap.get(keyword);
    }
}
