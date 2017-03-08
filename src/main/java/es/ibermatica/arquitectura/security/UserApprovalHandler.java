package es.ibermatica.arquitectura.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;

/**
 * Esta clase se encarga de ver si una determinada petición ya estaba aprovada por el usuario.
 *
 */
public class UserApprovalHandler extends ApprovalStoreUserApprovalHandler {

	private boolean useApprovalStore = true;

	private ClientDetailsService clientDetailsService;

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
		super.setClientDetailsService(clientDetailsService);
	}

	
	public void setUseApprovalStore(boolean useApprovalStore) {
		this.useApprovalStore = useApprovalStore;
	}

	/**
	 * Permite la aprobación automática de una "lista blanca" de clientes.
	 */
	@Override
	public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest,
			Authentication userAuthentication) {

//		boolean approved = false;
//		if (useApprovalStore) {
//			authorizationRequest = super.checkForPreApproval(authorizationRequest, userAuthentication);
//			approved = authorizationRequest.isApproved();
//		}
//		else {
//			if (clientDetailsService != null) {
//				Collection<String> requestedScopes = authorizationRequest.getScope();
//				try {
//					ClientDetails client = clientDetailsService
//							.loadClientByClientId(authorizationRequest.getClientId());
//					for (String scope : requestedScopes) {
//						if (client.isAutoApprove(scope) || client.isAutoApprove("all")) {
//							approved = true;
//							break;
//						}
//					}
//				}
//				catch (ClientRegistrationException e) {
//				}
//			}
//		}
//		authorizationRequest.setApproved(approved);
		authorizationRequest.setApproved(true);
		return authorizationRequest;

	}

}
