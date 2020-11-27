/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util.exceptions;

/**
 *
 * @author Ndadji Maxime
 */
public class ApplException extends Exception {
	private static final long serialVersionUID = 1L;

	public ApplException(Throwable cause) {
        super(cause);
    }

    public ApplException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplException(String message) {
        super(message);
    }
}
