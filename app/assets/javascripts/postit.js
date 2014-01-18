var app = angular.module("app", ["ngResource", "ngRoute"])
  .factory('Postit', ["$resource", function($resource){
    return $resource('postit/:id', { "id" : "@id" });
  }])
  .config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/', {
        templateUrl: 'partials/list.html',
        controller: 'PostitCtrl'
      }).
      when('/about', {
        templateUrl: 'partials/about.html'
      }).
      otherwise({
        redirectTo: '/'
      });
  }])
  .controller("PostitCtrl", ["$scope", "Postit", function($scope, Postit) {

    $scope.starsOnly = false;
    $scope.createForm = {};
    $scope.postits = Postit.query();

    $scope.create = function() {
      var postit = new Postit({text: $scope.createForm.text, star: false});
      postit.$save(function(){
        $scope.createForm = {};
        $scope.postits = Postit.query();
      })
    }

    $scope.remove = function(postit) {
      postit.$remove(function() {
        $scope.postits = Postit.query();
      })
    }

    $scope.update = function(postit) {
      postit.$save(function() {
        $scope.postits = Postit.query();
      })
    }

    $scope.starsOnlyFilter=function(){
      return function(postit) {
        if(!$scope.starsOnly) return true;
        else return postit.star;
      }
    }

}])
.directive('postit', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/postit.html'
  };
});