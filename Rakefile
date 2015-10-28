task :default => [:test]

desc "Test the output"
task :test do
  require 'html/proofer'
  HTML::Proofer.new("./output", { :verbosity => :debug }).run
end
