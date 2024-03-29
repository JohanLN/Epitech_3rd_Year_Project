import axios from 'axios';

export default class {

  constructor({config, routes, headers}) {
    this.axios = axios.create(config);
    this.routes = routes;
    this.getHeaders = headers;

    this.routes.map(route => {
      let httpMethod = route.httpMethod ? route.httpMethod : 'get';
      this[route.name] = async (opts = {}) => {
        let query = this.buildQuery(route.url, opts, route.args);
        let routeHeaders = route.headers ? route.headers.reduce((acc, h) => ({...acc, ...this.getHeader(h)}), {}) : {};
        let headers = {...this.headers(), ...routeHeaders}
        let postOpts = !route.opts ? {} : route.opts.reduce((acc, opt) => {
          acc[opt] = opts[opt];
          return acc;
        }, {});

        return (httpMethod == 'get'
          ? this.axios[httpMethod](query, {headers})
          : this.axios[httpMethod](query, postOpts, {headers}))
          .catch(this.errorHandler);
      };
    });
  }

  buildQuery(prefix, opts, args) {
    let secondaryArgsType = '?';
    let suffix = !args ? '' : args.map(arg => {
      let opt = opts[arg.name];

      if (arg.value)
        opt = arg.value;
      else if (!Object.keys(opts).includes(arg.name))
        return null;

      switch (arg.type) {
        case '/':
          return `/${opt}`
        case '&':
          let ret = `${secondaryArgsType}${arg.name}=${opt}`;
          secondaryArgsType = '&';
          return ret;
        default:
          throw new Error(`invalid arg type "${arg.type}"`);
      }
    }).join('');

    if (typeof prefix == 'function') {
      suffix = suffix.split('?')
      // console.log(prefix(suffix[0]), suffix)
      return prefix(suffix[0]) + (suffix[1] ? suffix[1] : '');
    }
    return prefix + suffix;
  }

  getHeader(name) {
    return this.getHeaders()[name];
  }
}
