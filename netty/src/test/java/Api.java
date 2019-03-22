import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.*;

/**
 * 网络接口
 * Created by lxf on 2016/10/19.
 */
public interface Api {
    @POST("getdataserver")
    Observable<ResponseModel> get(@Body RequestModel requestModel);

//    @POST("postdataserver")
//    Observable<ResponseModel> post(@Body RequestModel requestModel);
//
//
//    @Headers({
//            "Content-Type:text/xml;charset=utf-8",
//            "SOAPAction:http://webservices.izis.cn/Move_GetDate"
//    })
//    @POST("MyGoServicesPort")
//    Observable<ResponseBody> postSoap(@Body String body);
//
//    @Streaming
//    @GET
//    Observable<ResponseBody> down(@Url String url);
}
