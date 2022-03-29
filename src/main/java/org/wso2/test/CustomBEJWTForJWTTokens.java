package org.wso2.test;

import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.apimgt.gateway.dto.JWTInfoDto;
import org.wso2.carbon.apimgt.gateway.handlers.security.jwt.generator.APIMgtGatewayJWTGeneratorImpl;
import org.wso2.carbon.apimgt.gateway.handlers.security.jwt.generator.AbstractAPIMgtGatewayJWTGenerator;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component(
        enabled = true,
        service = AbstractAPIMgtGatewayJWTGenerator.class,
        name = "CustomBEJWTForJWTTokens"
)
public class CustomBEJWTForJWTTokens extends APIMgtGatewayJWTGeneratorImpl{

    @Override
    public Map<String, Object> populateStandardClaims(JWTInfoDto jwtInfoDto) {
        Map<String, Object> standardClaimsList = super.populateStandardClaims(jwtInfoDto);
        String dialect = getDialectURI();
        String excludeClaim = dialect + "/subscriber";
        String[] excludeClaims = {"/subscriber","/apiname"};
        System.out.println("Exclude claim list : " + excludeClaim);
        if(excludeClaims != null){
            //List<String> excludeClaimList = Arrays.asList(excludeClaim.split(",").clone());
            //excludeClaimList.stream().forEach(eachClaimUrl -> standardClaimsList.remove(eachClaimUrl));
            for(int i=0; i<excludeClaims.length; i++) {
                System.out.println(excludeClaims[i]);
                String temp = dialect + excludeClaims[i];
                System.out.println("temp variabale : " + temp);
                standardClaimsList.remove(temp);
            }
//
        }
        return standardClaimsList;
    }

    @Override
    public Map<String, Object> populateCustomClaims(JWTInfoDto jwtInfoDto) {

        Map<String, Object> claims = super.populateCustomClaims(jwtInfoDto);
        claims.put("uuid", UUID.randomUUID().toString());
        return claims;
    }
}
