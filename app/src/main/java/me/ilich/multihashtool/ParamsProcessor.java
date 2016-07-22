package me.ilich.multihashtool;

/**
 * Created by Aleks on 13.07.2016.
 */
public class ParamsProcessor {

    Sha1HashGenerator sha1HashGenerator = new Sha1HashGenerator();

    public OutputParams[] process(InputParams inputParams) {
        OutputParams vkOutputParams = new VkOutputParams(sha1HashGenerator.asPlain(inputParams.getAlias(),
                inputParams.getKeyStore(), inputParams.getStorePass()), inputParams.getPackageName(), inputParams.getClassName());
        OutputParams fbOutputParams = new FbOutputParams(sha1HashGenerator.asBase64(inputParams.getAlias(),
                inputParams.getKeyStore(), inputParams.getStorePass()), inputParams.getPackageName(), inputParams.getClassName());
        OutputParams[] outputParams = {vkOutputParams, fbOutputParams};
        return outputParams;
    }

}
