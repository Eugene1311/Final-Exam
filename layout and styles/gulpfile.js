var gulp = require('gulp'),
	sass = require('gulp-ruby-sass'),
	jade = require('gulp-jade'),
	browserSync = require('browser-sync'),
  imagemin = require('gulp-imagemin'),
  autoprefixer = require('gulp-autoprefixer'),
  less = require('gulp-less'),
  path = require('path');

var LessPluginAutoPrefix = require('less-plugin-autoprefix'),
    autoprefixer = new LessPluginAutoPrefix({browsers: ["last 2 versions"]});

//less
gulp.task('less', function () {
  return gulp.src('app/less/**/*.less')
    .pipe(less({
      paths: [ path.join(__dirname, 'less', 'includes') ],
      plugins: [autoprefixer]
    }))
    .pipe(gulp.dest('app/css'))
    .pipe(browserSync.reload({
      stream: true
    }));
});
//jade
gulp.task('jade', function() {
  return gulp.src('app/index.jade')
    .pipe(jade({
      pretty: true
    })) 
    .pipe(gulp.dest('app/'))
    .pipe(browserSync.reload({
      stream: true
    }))
});

gulp.task('browserSync', function() {
  browserSync({
    server: {
      baseDir: 'app'
    },
  })
});

gulp.task('images', function(){
  return gulp.src('app/img/**/*.+(png|jpg|jpeg|gif|svg)')
  .pipe(imagemin())
  .pipe(gulp.dest('app/images'))
});

gulp.task('watch', ['browserSync', 'less', 'jade'], function() {
  gulp.watch('app/less/**/*.less', ['less']); 
  // Reloads the browser whenever Jade or JS files change
  gulp.watch('app/*.jade', ['jade']); 
  gulp.watch('app/js/**/*.js', browserSync.reload);
});