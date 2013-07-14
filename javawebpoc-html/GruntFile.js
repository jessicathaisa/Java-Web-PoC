module.exports = function (grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            build: {
                src: ['src/config/**/*.js', 'src/controller/**/*.js', 'src/entity/**/*.js', 'src/service/**/*.js', 'src/util/**/*.js'],
                dest: 'build/app.min.js'
            }
        },
        typescript: {
            base: {
                src: ['src/**/*.ts'],
                options: {
                    module: 'amd',
                    target: 'es5'
                }
            }
        },
        watch: {
            files: 'src/**/*.ts',
            tasks: ['typescript']
        },
        bower: {
            install: {
                options: {
                    targetDir: './src/lib',
                    layout: 'byComponent'
                }
                //just run 'grunt bower:install' and you'll see files from your Bower packages in lib directory
            }
        },
        connect: {
            server: {
                options: {
                    port: 8080,
                    base: 'src/'
                }
            }
        },
        open: {
            dev: {
                path: 'http://localhost:8080/index.html'
            }
        }
    });

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-typescript');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-open');

    // Default task(s).
    grunt.registerTask('default', ['connect', 'open', 'watch']);
    grunt.registerTask('build', ['typescript', 'uglify']);

};
