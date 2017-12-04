'use strict';
var app = angular.module('graph', ['ngVis']);

app.controller('MainCtrl', ['$scope', '$http', 'VisDataSet', function($scope, $http, VisDataSet) {
  $scope.title = 'Graph Application - Hamiltonian Cycle';
  $scope.filePath = "c://tmp"

  $scope.setVariation1  = function() {
    $scope.key = 1;
    $scope.sendVariation();
  };

  $scope.setVariation2  = function() {
    $scope.key = 2;
    $scope.sendVariation();
  };

  $scope.setVariation3  = function() {
    $scope.key = 3;
    $scope.sendVariation();
  };

  $scope.sendVariation = function()  {
    if($scope.fileName === undefined || $scope.filePath === undefined)  {
      $scope.message = "Path or file name is undefined";
      return;
    }

    $http({
      method: 'POST',
      contentType: 'application/text',
      url: 'hamiltonian/',
      params:{"fileName": $scope.fileName, "filePath": $scope.filePath, "variation": $scope.variation, "key": $scope.key}
    }).then(function (response) {
      if(response.message !== undefined)
        $scope.message = response.data;
      else
        $scope.result1 = response.data;
      console.log($scope.result1);
    });
  };

  $scope.loadGraph = function() {
    $scope.options = {
      "improvedLayout": false
    };
    console.log('result1 is' + $scope.result1);

    var path = JSON.parse("[" + $scope.result1 + "]");

    if(path.length > 500)  {
      $scope.message = "Cannot display greater than 500 nodes";
      return;
    }

    if(path.length > 1)  {
      var vertex = [];
      for(var i = 0; i < path.length - 1; ++i)  {
        var obj = {
          id: path[i],
          label: path[i].toString()
        };
        vertex[i] = obj;
      }

      var arrow = undefined;
      if($scope.variation === 'true')
        arrow = 'to';
      var edge = [];
      for(i = 0; i < path.length - 1; ++i)  {
        obj = {
          from: path[i],
          to: path[i+1],
          arrows:arrow
        };
        edge[i] = obj;
      }

      console.log('Here');
      $scope.data = {
        nodes: vertex,
        edges: edge
      };

      console.log($scope.data);
    }
    else {
      $scope.message = "To few nodes to display";
      return;
    }
  };

  $scope.sendFileData = function() {

    $http({
      method: 'POST',
      contentType: 'application/text',
      url: 'hamiltonian/',
      params:{"fileName": $scope.fileName, "filePath": $scope.filePath}
    }).then(function (response) {
      $scope.result1 = response.data;
      console.log($scope.result1);
    });
  };
}]);

// app.directive('fileReader', function() {
//   return {
//     scope: {
//       fileReader:"="
//     },
//     link: function(scope, element) {
//       $(element).on('change', function(changeEvent) {
//         var files = changeEvent.target.files;
//         if (files.length) {
//           var r = new FileReader();
//           r.onload = function(e) {
//               var contents = e.target.result;
//               scope.$apply(function () {
//                 scope.fileReader = contents;
//                 scope.testing = contents;
//               });
//           };
//
//           r.readAsText(files[0]);
//         }
//       });
//     }
//   };
// });
