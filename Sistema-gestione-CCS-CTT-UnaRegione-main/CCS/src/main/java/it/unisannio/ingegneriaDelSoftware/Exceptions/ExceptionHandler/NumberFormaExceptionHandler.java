package it.unisannio.ingegneriaDelSoftware.Exceptions.ExceptionHandler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**Nel momento in cui si verificano degli {@link NumberFormatException} significa che è stato riscontrato un problema nel controllo del formato dell'argomento passato

 /**Un handler che si occupa di elaborare una risposta nel momento in cui viene sollevata dal server
 * una {@link NumberFormatException}*/

public class NumberFormaExceptionHandler implements ExceptionMapper<NumberFormatException> {
	
    @Override
    public Response toResponse(NumberFormatException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}