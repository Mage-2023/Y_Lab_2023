package Wallet.services;

import Wallet.dto.in.SignInForm;

import java.util.Optional;

public interface SignInService {
    boolean doAuthenticate(SignInForm form);
}
