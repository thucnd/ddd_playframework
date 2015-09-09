app
  .factory('MemberFactory', ['$http','AuthenFactory', function ($http, AuthenFactory) {
    var headers = {'Content-Type': 'application/x-www-form-urlencoded'};
    var member = {};
    var urlBase = '/app';

    return {
      set: function (mData) {
        member.name = mData.name;
        member.email = mData.email;
      },
      get: function () {
        return {
          name: member.name,
          email: member.email
        }
      },
      create: function (param) {
        return $http({
          method: 'POST',
          url: urlBase + '/register',
          data: $.param(param),
          headers: headers
        });
      },
      authenticate: function (param) {
        return $http({
          method: 'POST',
          url: urlBase + '/login',
          data: $.param(param),
          headers: headers
        });
      }
    }
  }]);
