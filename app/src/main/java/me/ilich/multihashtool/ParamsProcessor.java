package me.ilich.multihashtool;

/**
 * Created by Aleks on 13.07.2016.
 */
public class ParamsProcessor {
    public OutputParams[] process(InputParams inputParams) {
        OutputParams vkOutputParams = new VkOutputParams(new Sha1HashGenerator().asPlain(inputParams.getAlias(),
                inputParams.getKeyStore(), inputParams.getStorePass()));
        OutputParams fbOutputParams = new FbOutputParams(new Sha1HashGenerator().asBase64(inputParams.getAlias(),
                inputParams.getKeyStore(), inputParams.getStorePass()));
        OutputParams[] outputParams = {vkOutputParams, fbOutputParams};
        return outputParams;
    }
}
