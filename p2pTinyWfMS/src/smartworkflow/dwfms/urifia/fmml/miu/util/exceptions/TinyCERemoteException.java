package smartworkflow.dwfms.urifia.fmml.miu.util.exceptions;


public class TinyCERemoteException extends ApplException{
	
	private static final long serialVersionUID = 1L;
	
	public TinyCERemoteException(String message) {
        super( message );
    }
    public TinyCERemoteException(String message, Throwable cause) {
        super( message, cause );
    }
    public TinyCERemoteException(Throwable cause) {
        super( cause );
    }
}
