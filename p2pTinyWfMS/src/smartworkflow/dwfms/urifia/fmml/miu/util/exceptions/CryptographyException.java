/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util.exceptions;

/**
 *
 * @author Ndadji Maxime
 */
public class CryptographyException extends Throwable {
	private static final long serialVersionUID = 1L;

	public CryptographyException(Throwable cause) {
        super(cause);
    }

    public CryptographyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptographyException(String message) {
        super(message);
    }
}
