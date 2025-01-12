package com.zerotrust.identity_auth_system.filter;

@Component
public class PasswordValidationFilter extends OncePerRequestFilter {

    private final PasswordManager passwordManager;

    public PasswordValidationFilter(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String username = request.getHeader("Username");
        String password = request.getHeader("Password");

        if (username == null || password == null || !passwordManager.validatePassword(username, password)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid password");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
