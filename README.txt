Include this library in the two applications that you want to communicate
The first application will generate the Token (getSecurityToken)
The sencond application will receive the Token and will have to validate it (validateSecurityToken) for a range of time.
Customize your own private.key
The algorithm uses sha-256 and base32 codification