// Global auth-aware fetch helper for CasCached
(function(){
  function getContextPath() {
    const meta = document.querySelector('meta[name="context-path"]');
    if (meta && meta.content) return meta.content;
    // Default context path for the application
    return '/fd-simulator';
  }

  const CONTEXT = getContextPath();

  function joinUrl(url) {
    if (!url) return url;
    // Absolute URL
    if (/^https?:\/\//i.test(url)) return url;
    // Already includes context path
    if (CONTEXT && url.startsWith(CONTEXT + '/')) return url;
    // Root-relative URL
    if (url.startsWith('/')) return CONTEXT + url;
    // Relative URL
    return CONTEXT + '/' + url.replace(/^\/+/, '');
  }

  async function apiFetch(url, options = {}) {
    const opts = { ...options };
    opts.headers = new Headers(opts.headers || {});

    // Default Content-Type for JSON bodies
    const hasBody = typeof opts.body !== 'undefined' && opts.body !== null;
    if (hasBody && !opts.headers.has('Content-Type')) {
      opts.headers.set('Content-Type', 'application/json');
    }

    // Attach Authorization header unless explicitly disabled
    const skipAuth = opts.skipAuth === true;
    const token = localStorage.getItem('token');
    if (!skipAuth && token && !opts.headers.has('Authorization')) {
      opts.headers.set('Authorization', 'Bearer ' + token);
    }

    // Clean custom flags
    delete opts.skipAuth;

    const finalUrl = joinUrl(url);
    const res = await fetch(finalUrl, opts);

    // If unauthorized, clear and redirect to login
    if (res.status === 401) {
      try {
        localStorage.removeItem('token');
        localStorage.removeItem('userRole');
        localStorage.removeItem('username');
      } catch {}
      const loginPath = joinUrl('/login');
      if (window.location.pathname !== loginPath) {
        window.location.href = loginPath;
      }
    }

    return res;
  }

  // Helper for POSTing JSON
  function postJson(url, data, opts = {}) {
    return apiFetch(url, { method: 'POST', body: JSON.stringify(data), ...opts });
  }

  // Expose globally
  window.apiFetch = apiFetch;
  window.postJson = postJson;
  window.__FD_CONTEXT__ = CONTEXT;
})();
