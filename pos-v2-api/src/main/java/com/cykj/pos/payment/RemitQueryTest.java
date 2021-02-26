/**
 * Created by jiashuai.bao on 2019-06-17.
 */

package com.cykj.pos.payment;

import com.sec.sdk.SecClient;
import com.sec.sdk.bean.BaseResponse;
import com.sec.sdk.bean.RemitQueryRequestDTO;
import com.sec.sdk.constants.SecGatewayConstants;
import org.junit.Test;

/**
 * 对应文档 5.2 付款订单查询
 **/

public class RemitQueryTest {

    /**
     * 商户私钥 请根据文档描述生成并妥善保管！
     * */
    String merPrivate = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC7L0j69Cstq23OOd2zcim0UZo1WVynbRA8jyRTTERA7k59b5bqnAqjOAWXqc7In/a0IDKe+wgSjjxpRiQOCX5g0Ai+xtzHFQS57kJrK6NKYhBMSFIsgeeDTY17flzu9WJcJoxpzqZ42Xz0x7DgpVFO1rNfiVPS95WhUyWz3WGdOaYYgL0OlwoNAwdU6KoquhLXcnnyMh6FTkWWN2I3Q5+rzRAhNnKQLJZqGXTOXS4FrR0BCTdMC9uQNMU4ZJ+7dkrSSxL7WgsO1YtLqvpS6qGmTwg3kxdVt1lSe+AJSISl8aBdCKY6dNA2ftXwBTcl+COuIKQrOA/5ajXaqbs11YqRAgMBAAECggEAFoA6n6AWSKkgy/d8xQNmK/zbMqqJTr73grAaR8EVO1DqMn3lgFI9nR0w3uhUqR1BhKmSLDY05DYg4O7DisRtYqnfQz+Ybn4CUW9ooIVgXaKoHuNarWr4L3p1FXE9LqRkrHsdyeJDdKeAM9SFWJvGyFlaupNBdky4uenK0HI+N63vvnSSGXsamr73YMT9b+ShgPcb4oP+kfuAsbG6PzCyp0At5LaSGvGQ0h9FDMoaCaQStjqoJaWzneCW768qikzoMBtXC8GG3rROi63AFiqy3xJukPtH8JV0xkcuPU9inhoAh7g643Y24oLnMpgPH+RrRc7vlPXDQNyAbc74eoTIoQKBgQDbcwKMbABfCfAUtqJdIRQlo9gz+ZjV5saQI3VKKAkpcum9iQMtGbfhkv9RzFZOHlmqjM0rt5VTtDKtWm34PwXMd90Ee6Kw+cSLPewwdmwVNa4bPUKwLxiSOKoIsAA0dnQ4CcpoFNdFvhth/y0ZD32AsL4ocLU6BSftmZBjDEiz/QKBgQDaXJALZL4uCfod+dXLaq18MfU7tvYviI+M/CK82N128FSx6FG5v2L4s3vpCK760XxAAth3dI6qlA5qRFI4LHQNXaE7b+eetJTTmalZXUf8wIZ8bqczfMDJ+Bc8Ik/rZqWudanpzGloIaHak/79ye8xYL/UkyXTmBwGicdQ9wLTJQKBgDy6XwItAQ1L2rxgrh3HgaPI65O2hqzq6BgLN/IFkkN+IWDqAY8BhvlNjIo+WcPUckvYGw72BsTbIQsH81a3WgtzQB51eFgxRkqiIDJpEw/rgvrWwRWwcsREGgm9atEZ1p+LVg/ndItASKOd0gUVXE24m2blicE8nAgMbwB57cShAoGAPMDoKuNaroCEIHb0buY9YoKb0oNzN4JtPjKgj55e78qqJd2Xb3C6XqvBW/LYjFxgj4a7J3E8iGUtJY/guFRV7aoRQ/I8pUFk7wKfxYdYkhAW2uQGjSK2mPr3q/l39uzWSBXgsnX3FcvVtnTay021yS078i8GpTp7Aa6vz+0J0ikCgYEA1ymNcPMJ6fjRZpL1AQ0zDnylf+rLMxnqfu40LCAzLk+UMppxuuD1B8JKtQ0GxiwDFss0VSitQhj8RFdD1rVM+Ugn8YpvDbY5xC9i0J3lsm1h5iu8ypaSuXeI9BmTqJweOZmT5x/rAtIrViYSKEfY5Pva32K7sX0LBEcv+ykC+3I=";

    @Test
    public void httpTest() throws Exception{

        //商户应用ID
        String appId = "100";

        SecClient secClient = new SecClient("http://39.97.110.167/sop/gateway","settle.remit.api.query", appId,merPrivate,"001","2013-01-01 08:08:08",20000,20000);

        //业务参数构建
        RemitQueryRequestDTO requestDTO = new RemitQueryRequestDTO();
        requestDTO.setCustBatchNo("");

        BaseResponse responseDTO = secClient.excute(requestDTO);
        System.out.println(responseDTO.toString());
        System.out.println(responseDTO.getResponse());
    }
}
