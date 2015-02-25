======================
 Development workflow
======================

.. contents::
..
    1  Introduction
    2  How to send a patch
    3  Coding conventions in Sympy
      3.1  Standard Python coding conventions
      3.2  Documentation strings
      3.3  Python 3
    4  Workflow process
      4.1  Create your environment
        4.1.1  Install mpmath
        4.1.2  Install git
        4.1.3  Install other software
        4.1.4  Basic git settings
        4.1.5  Advanced tuning
        4.1.6  Create GitHub account
        4.1.7  Cloning SymPy
        4.1.8  Set up SSH keys
      4.2  Create separated branch
      4.3  Code modification
      4.4  Be sure that all tests of SymPy_ pass
      4.5  Commit the changes
      4.6  Writing commit messages
      4.7  Create a patch file or pull request for GitHub
      4.8  Writing pull request title and description
      4.9  Updating your pull request
      4.10  Synchronization with master `sympy/sympy`
        4.10.1  Merging
        4.10.2  Rebasing
        4.10.3  Changing of commit messages
    5  Reviewing patches
      5.1  Manual testing
      5.2  Sympy-bot
      5.3  Requirements for inclusion
    6  References

Introduction
============

In SymPy_ we encourage collaborative work.

Everyone is welcome to join and to implement new feature, fix some bug, give
general advice, etc. Also, we try to discuss everything and to review each
other's work so that many eyes can see more thus raising the quality.

General discussion takes place on `sympy@googlegroups.com`_ mailing list and
in the issues_. Some discussion also takes place on IRC (our channel is
`#sympy at freenode`_).

As some of you already know, software development is not just coding. Many
non-coding tasks have to be done in order to produce *good* code. For
example: setting up infrastructure, designing, testing, documenting,
assisting new developers (we are doing it here), and of course programming.

But even programming is not all about writing the code, it is about writing the
code *and* preparing it so that the code can be included in the project.

Both producing the code and bringing it to the project are important parts of
the game -- without the code there is nothing to bring in, and having the code
outside is a no-win for anyone.

As already said above, we review changes. This idea was borrowed from
successful projects like Linux, Python, SAGE and many more. In short, each
change is first reviewed by other developers and only when it is approved
is the code pushed in.

Like it takes effort to write good and clear code, reviewing other's work needs
effort too. There are good practices how to do this so that reviewing is fun
for both the author and the reviewer. We try to follow these good practices, and
we'll try to show you how to follow them too.

When reviewing other's patches you *learn* a lot, so why not participate
as a reviewer too? Anyone regardless of technical skill can help review code,
and it's an excellent way for newcomers to learn about Sympy's development
process and community.


How to send a patch
===================

License: `New BSD License`_ (see the `LICENSE`_ file for details) covers all files in the SymPy repository unless stated otherwise.

There are a few ways to create and send a patch.

The best way is to send a GitHub pull request against the `sympy/sympy`_ repository. We'll review it and push it in.
The GitHub pull request is the preferred method, because it makes it easy for us to review and push the code in.

More quickly, but not convenient for reviewing and merging, is to create a patch-file using git alone.
This way can be used if the patch has a high-priority or is significant, only one or two files are
involved, or you don't have enough time to use the preferred method.

Although we are grateful for any improvements of Sympy, we strongly recommend you submit your patches as
pull requests: this will greatly speed up the processing of the patch and ensure that it doesn't get
forgotten due to inactivity.

The basic work-flow for both variants is a follows:

1. Create your environment, if it was not created earlier.
2. Create a new branch.
3. Modify code and/or create tests of it.
4. Be sure that all tests of SymPy pass.
5. Only then commit changes.
6. Create patch file, or pull request from GitHub.

All those are described in the details below `Workflow process`_, but before
you read that, it would be useful to acquaint yourself with `Coding
conventions in Sympy`_.

If you have any questions you can ask them on the `mailinglist`_.


Coding conventions in Sympy
===========================

Standard Python coding conventions
----------------------------------

Follow the standard Style Guide for Python Code when writing code for SymPy, as explained at the following URLs:

    - http://www.python.org/dev/peps/pep-0008
    - http://www.python.org/dev/peps/pep-0257

In particular,

- Use 4 spaces for indentation levels.

- Use all lowercase function names with words separated by
  underscores. For example, you are encouraged to write Python
  functions using the naming convention

  ::

      def set_some_value()

  instead of the CamelCase convention.

- Use CamelCase for class names and major functions that create
  objects, e.g.

  ::

      class PolynomialRing(object)

Note, however, that some functions do have uppercase letters where it makes sense. For example, for matrices they are LUdecomposition or T (transposition) methods.

Documentation strings
---------------------

``prime``'s docstring is an example of a well formatted docstring::

        """Return the nth prime.

	Primes are indexed as prime(1) = 2, prime(2) = 3, etc.... The nth prime is
	approximately n*log(n) and can never be larger than 2**n.

	See Also
	========
	sympy.ntheory.primetest.isprime, primerange, primepi

	References
	==========
	.. [1] http://primes.utm.edu/glossary/xpage/BertrandsPostulate.html

	Examples
	========
	>>> from sympy import prime
	>>> prime(10)
	29
	>>> prime(1)
	2

	"""

For more information see [[Writing documentation]] article on wiki.

Python 3
--------

SymPy uses a single codebase for Python 2 and Python 3 (the current supported
versions are Python 2.6, 2.7, 3.2, and 3.3). This means that your code needs
to run in both Python 2 and Python 3.

To make this easier, there are many functions in ``sympy.core.compatibility``
that should be used when there are differences between the two Python
versions.  To see what things you can import from that file, look at `its
source
<https://github.com/sympy/sympy/blob/master/sympy/core/compatibility.py>`_.

If you need to use additional functions or methods that change names
from Python 2 to Python 3, it is far better to add to
``sympy.core.compatibility`` and import from there than to bloat
individual source files with version-specific logic.
In general, the Python 3 names are to be preferred, but this is not an
absolute requirement.  For example, the current codebase uses xrange extensively.

You should also make sure that you have::

    from __future__ import print_function, division

at the top of each file. This will make sure that ``print`` is a function, and
that ``1/2`` does floating point division and not integer division. You should
also be aware that all imports are absolute, so ``import module`` will not
work if ``module`` is a module in the same directory as your file.  You will
need to use ``import .module``.

Workflow process
================

Create your environment
-----------------------

Creating of environment is once-only.

Install mpmath
~~~~~~~~~~~~~~

SymPy has a hard dependency on the `mpmath <http://mpmath.org/>`_ library (version >= 0.19). You should install it first, please refer to the mpmath `installation guide <https://github.com/fredrik-johansson/mpmath#1-download--installation>`_.

Install git
~~~~~~~~~~~

To install `git` in Linux-like systems you can do it via your native package management system: ::

    $ yum install git

or::

    $ sudo apt-get install git

In Windows systems, first of all, install Python from::

    http://python.org/download/

by downloading the "Python 2.7 Windows installer" (or Python 2.6 or 2.5) and running it. Then do not
forget to add Python to the $PATH environment.

On Windows and Mac OS X, the easiest way to get git is to download GitHub's
software, which will install git, and also provide a nice GUI (this tutorial
will be based on the command line interface). Note, you may need to go into
the GitHub preferences and choose the "Install Command Line Tools" option to
get git installed into the terminal.

If you do decide to use the GitHub GUI, you should make sure that any "sync
does rebase" option is disabled in the settings.

Install other software
~~~~~~~~~~~~~~~~~~~~~~

Sympy development uses a few tools that are not included in a basic Python distribution.  You won't really need them until you are getting ready to submit a pull request, but to save time later, you can install:

* Sphinx documentation generator (package sphinx-doc on Debian-based systems)
* Python coverage library (package python-coverage)
* Programs needed for building docs, such as rsvg-convert. An up-to-date list is is 
  maintained in doc/README.rst

Basic git settings
~~~~~~~~~~~~~~~~~~

Git tracks who makes each commit by checking the user’s name and email.
In addition, we use this info to associate your commits with your GitHub account.

To set these, enter the code below, replacing the name and email with your own (`--global` is optional).::

    $ git config --global user.name "Firstname Lastname"
    $ git config --global user.email "your_email@youremail.com"

The name should be your actual name, not your GitHub username.

These global options (i.e. applying to all repositories) are placed in `~/.gitconfig`.
You can edit this file to add setup colors and some handy shortcuts: ::

    [user]
        name = Firstname Lastname
        email = your_email@youremail.com

    [color]
        diff  = auto
        status= auto
        branch= auto
        interactive = true

    [alias]
        ci = commit
        di = diff --color-words
        st = status
        co = checkout
        log1 = log --pretty=oneline --abbrev-commit
        logs = log --stat

Advanced tuning
~~~~~~~~~~~~~~~
It can be convenient in future to tune the bash prompt to display the current git branch.

The easiest way to do it, is to add the snippet below to your .bashrc or .bash_profile::

    PS1="[\u@\h \W\$(git branch 2> /dev/null | grep -e '\* ' | sed 's/^..\(.*\)/{\1}/')]\$ "

But better is to use `git-completion` from the `git` source. This also has the advantage of adding tab completion to just about every git command. It also includes many other useful features, for example,
promptings. To use `git-completion`, first download the `git` source code (about 27 MiB), then copy
the file to your profile directory::

    $ git clone git://git.kernel.org/pub/scm/git/git.git
    $ cp git/contrib/completion/git-completion.bash ~/.git-completion.sh

Read instructions in '~/.git-completion.sh'

Note that if you install git from the package manager in many Linux distros, this file is already installed for you.  You can check if it is installed by seeing if tab completion works on git commands (try, e.g., `git commi<TAB>`, or `git log --st<TAB>`). You can also check if the PS1 commands work by doing something like::

    $ PS1='\W $(__git_ps1 "%s")\$ '

And your command prompt should change to something like::

    sympy master$

Note, it is important to define your PS1 using single quotes ('), not double quotes ("), or else bash will not update the branch name.

Create GitHub account
~~~~~~~~~~~~~~~~~~~~~

As you are going to use `GitHub`_  you should have a GitHub account. If you have not one yet then sign up at:

    - https://github.com/signup/free

Then create your own *fork* of the SymPy project (if you have not yet). Go to the SymPy GitHub repository:

    - https://github.com/sympy/sympy

and click the “Fork” button.

    [[img/dev-guide-forking.png]]

Now you have your own repository for the SymPy project. If your username in GitHub is `mynick` then the address of the forked project will look something like:

    - https://github.com/mynick/sympy

    [[img/dev-guide-forking-result.png]]

Some tools connect to GitHub without SSH. To use these tools properly you need to find and configure your API Token.

On GitHub, click `“Account Settings”` then `“Account Admin.”`

    [[img/dev-guide-apitoken.png]]

Enter the code below, replacing the `mynick` and `012-api-token` with your own::

    $ git config --global github.user mynick
    $ git config --global github.token 012-api-token

*Note*: if you ever change your GitHub password, a new token will be created and will need to be updated.

*Note*: GitHub no longer uses API tokens. You can skip this step.

Cloning SymPy
~~~~~~~~~~~~~

On your machine browse to where you would like to store SymPy, and clone (download) the latest
code from SymPy's original repository (about 20 MiB)::

    $ git clone git://github.com/sympy/sympy.git
    $ cd sympy

Then assign your read-and-write repo to a remote called "github"::

    $ git remote add github git@github.com:mynick/sympy.git



For more information about GitHub forking and tuning see: [8]_, [9]_ and [11]_.

Set up SSH keys
~~~~~~~~~~~~~~~

To establish a secure connection between your computer and GitHub see detailed instructions in [11]_.

If you have any problems with SSH access to GitHub, read the troubleshooting instructions at [12]_, or ask us in mail-list.

And now, do not forget to go to the `Create separated branch`_ instructions before modifying the code.

Virtual Environments
~~~~~~~~~~~~~~~~~
You may want to take advantage of using virtual environments to isolate your development version of SymPy from any system wide installed versions, e.g. from ``apt-get install python-sympy``. There are two leading virtual environment tools, `virtualenv <https://virtualenv.pypa.io>`_ and `conda <http://conda.pydata.org/>`_. Conda has the advantage that all software installs are binary and that you can easily switch between python versions. Here is an example of using conda to create two development virtual environments, one for python 2.7 and one for python 3.4::

  $ conda create -n sympy-dev-py27 python=2.7 mpmath>=0.19
  $ conda create -n sympy-dev-py34 python=3.4 mpmath>=0.19

You now have two environments that you can use for testing your development copy of SymPy. For example, clone your SymPy fork from Github::

  $ git clone git@github.com:<your-github-username>/sympy.git
  $ cd sympy

Now activate the Python 2.7 environment::

  $ source activate sympy-dev-py27

Note that on Windows ``source`` is not required in the command. And run the SymPy tests::

  (sympy-dev-py27)$ bin/test 

After the tests run, then try the tests in the Python 3.4 environment::

  (sympy-dev-py27)$ source deactivate
  $ source activate sympy-dev-py34
  (sympy-dev-py34)$ bin/test

You can also install SymPy into the environment if you wish (so you can use the development version from any location on your filesystem)::

  (sympy-dev-py34)$ python setup.py install

If you prefer virtualenv, the process is similar, except the switch between Python 2 and 3 isn't as simple.

Create a separate branch
------------------------

Typically, you will create a new branch to begin work on a new issue. Also pull request related with them.

A branch name should briefly describe the topic of the patch or pull request.
If you know the issue number, then the branch name could be, for example, `1234_sequences`. To create
and checkout (that is, make it the working branch) a new branch ::

    $ git branch 1234_sequences
    $ git checkout 1234_sequences

or in one command using ::

    $ git checkout -b 1234_sequences

To view all branches, with your current branch highlighted, type::

    $ git branch

And remember, **never type the following commands in master**: `git merge`, `git commit`, `git rebase`.



Code modification
-----------------

...

Do not forget that all new functionality should be tested, and all new methods, functions, and classes should have doctests showing how to use them.

Keep in mind, doctests are *not* tests. Think of them as examples that happen to be tested. Some key differences:

- write doctests to be informative; write regular tests to check for regressions and corner cases. 
- doctests can be changed at any time; regular tests should not be changed.

In particular, we should be able to change or delete any doctest at any time if it makes the docstring better to understand.

Be sure that all tests of SymPy_ pass
-------------------------------------

To ensure everything stays in shape, let’s see if all tests pass::

    $ ./bin/test
    $ ./bin/doctest

Each command will show a *DO NOT COMMIT* message if any of the tests it runs does not pass.

bin/test and bin/doctest do fast tests (those that take seconds). You'll want to run them whenever your code is supposed to work and not break anything.

You can also run ``bin/test --slow``, to run the slow tests (those that may
take minutes each).

Code quality (unwanted spaces and indents) are checked by *./bin/test* utilities too. But you can separately run this test with the help of this command::

    $ ./bin/test quality

If you have trailing whitespace it will show errors. This one will fix unwanted spaces.

    $ ./bin/strip_whitespace <file>

If you want to test only one set of tests try::

    $ ./bin/test sympy/concrete/tests/test_products.py

But remember that all tests should pass before committing.

Note that all tests will be run when you make your pull request automatically
by Travis CI, so do not worry too much about running every possible test. You
can usually just run::

    $ ./bin/test mod
    $ ./bin/doctest mod

where ``mod`` is the name of the module that you modified.

Commit the changes
------------------

You can check what files are changed::

    $ git status

Add new files to the index if necessary::

    $ git add new_file.py

Check total changes::

    $ git diff

You are ready to commit changes locally. A commit also contains a `commit
message` which describes it.  See the next section for guidelines on writing
good commit messages. Type::

    $ git commit

An editor window will appear automatically in this case. In Linux, this is vim by default. You
can change what editor pops up by changing the `$EDITOR` shell variable.

Also with the help of option `-a` you can tell the command `commit` to automatically stage files
that have been modified and deleted, but new files you have not told git about will not be
affected, e.g.,::

    $ git commit -a

If you want to stage only part of your changes, you can use the interactive commit feature.  Just type::

    $ git commit --interactive

and choose the changes you want in the resulting interface.

Writing commit messages
-----------------------

The commit message has two parts: a title (first line) and the body. The two
are separated by a blank line.

There are only two formatting rules for commit messages

- There should be a single line summary of 71 characters or less which allows
  the one-line form of the log to display the summary without wrapping. A
  common convention is to not end the summary with a period (full stop).

- Additional details can be given after the summary. **Make sure to leave a
  blank line after the summary** and to keep all lines to 78 characters or less
  so they can be easily be read in terminals which don't automatically wrap lines.

Here is an example commit message (from the commit
`[bf0e81e12a2f75711c30f0788daf4e58f72b2a41]
<https://github.com/sympy/sympy/commit/bf0e81e12a2f75711c30f0788daf4e58f72b2a41>`_,
which is part of the SymPy history)::

    integrals: Improved speed of heurisch() and revised tests

    Improved speed of anti-derivative candidate expansion and solution
    phases using explicit domains and solve_lin_sys(). The upside of
    this change is that large integrals (those that generate lots of
    monomials) are now computed *much* faster. The downside is that
    integrals involving Derivative() don't work anymore. I'm not sure
    if they really used to work properly or it was just a coincidence
    and/or bad implementation. This needs further investigation.

    Example:

    In [1]: from sympy.integrals.heurisch import heurisch

    In [2]: f = (1 + x + x*exp(x))*(x + log(x) + exp(x) - 1)/(x + log(x) + exp(x))**2/x

    In [3]: %time ratsimp(heurisch(f, x))
    CPU times: user 7.27 s, sys: 0.04 s, total: 7.31 s
    Wall time: 7.32 s
    Out[3]:
       ⎛ 2        x                 2⋅x      x             2   ⎞
    log⎝x  + 2⋅x⋅ℯ  + 2⋅x⋅log(x) + ℯ    + 2⋅ℯ ⋅log(x) + log (x)⎠          1
    ──────────────────────────────────────────────────────────── + ───────────────
                                 2                                      x
                                                                   x + ℯ  + log(x)

    Previously it took 450 seconds and 4 GB of RAM to compute.

Some things to note about this commit message:

- The first line gives a brief description of what the commit does. Tools like
  ``git shortlog`` or even GitHub only show the first line of the commit by
  default, so it is important to convey the most important aspects of the
  commit in the first line.

- The first line has ``integrals:``, which gives context to the commit. A
  commit won't always be seen in the context of your branch, so it is often
  helpful to give each commit some context. This is not required, though, as
  it is not hard to look at the commit metadata to see what files were
  modified or at the commit history to see the nearby related commits.

- After the first line, there is a paragraph describing the commit in more
  detail. This is important, as it describes what the commit does, which might
  be hard to figure out just from looking at the diff. It also gives
  information that might not be in the diff at all, such as known issues. Such
  paragraphs should be written in plain English. Commit messages are intended
  for human readers, both for people who will be reviewing your code right
  now, and for people who might come across your commit in the future while
  researching some change in the code. Sometimes, bullet lists are a good
  format to convey the changes of a commit.

- Last, there is an example.  It is nice to give a concrete example in commits
  that add new features.  This particular example is about improving the speed
  of something, so the example is a benchmark result.

Note that you may feel free to use Unicode characters in commit messages, such
as output from the SymPy Unicode pretty printer.

Try to avoid short commit messages, like "Fix", and commit messages that give
no context, like "Found the bug".  When in doubt, a longer commit message is
probably better than a short one.

Create a patch file or pull request for GitHub
----------------------------------------------

Be sure that you are in your own branch, and run::

    $ git push github 1234_sequences

This will send your local changes to your fork of the SymPy repository.
Then navigate to your repository with the changes you want someone else to pull:

    https://github.com/mynick/sympy

Select branch, and press the `Pull Request` button.

    [[img/dev-guide-pull-1-2.png]]

After pressing the `Pull Request` button, you are presented with a preview page containing
* a textbox for the **title**
* a textbox for the **description**, also referred to as the opening paragraph (OP)
* the commits that are included

    [[img/dev-guide-pull-2.png]]

The title and description may already have been pre-filled but they can be changed (see 
`Writing pull request title and description`_). 
Markdown is supported in the description, so you
can embed images or use preformatted text blocks.

    [[img/dev-guide-pull-3.png]]

You can double check that you are committing the right changes by
* switching to the `Commits` tab to see which commits are included (sometimes unintended commits can be caught this way)
* switching to the `Files Changed` tab to review the diff of all changes

When you are ready, press the `Send pull request` button. The pull request is sent immediately and 
you’re taken to the main pull request discussion and review page. Additionally, all repository collaborators and followers will see an event in their dashboard.

If there isn't an issue that the pull request addresses, one should be created so even if the 
pull request gets closed there is a redundant reference to it in the issues.

See also `Updating your pull request`_

Writing pull request title and description
------------------------------------------

You might feel that all your documentation work is done if you have made good commit messages.
But a good title and description will help in the review process.

The title should be brief but descriptive.
 
* **don't** write "fixes #1234" there; such references are more useful in the description section.
* **do** include the prefix "[WIP]" if you aren't ready to have the pull request merged and remove the prefix when you *are* ready

The description is a good place to 

* show what you have done, perhaps comparing output from master with the output after your changes
* refer to the issue that was addressed like "#1234"; that format will automatically create a link to the corresponding issue or pull request, e.g. "This is similar to the problem in issue #1234...". This format also works in the discussion section of the pull request.
* use phrases like "closes #1234" or "fixed #1234" (or similar that `follow the auto-close syntax <https://help.github.com/articles/closing-issues-via-commit-messages>`_) then those other issues or pull requests will be closed when your pull request is merged. Note: this syntax does not work in the discussion of the pull request.

See also `github's own guidelines for pull requests <https://github.com/blog/1943-how-to-write-the-perfect-pull-request>`_

Updating your pull request
--------------------------

If you need to make changes to a pull request there is no need to close it.
The best way to make a change is to add a new commit in you local repository
and simply repeat push command::

    $ git commit
    $ git push github 1234_sequences

Note that if you do any rebasing or in any way edit your commit history, you will have to add
the `-f` (force) option to the push command for it to work::

    $ git push -f github

You don't need to do this if you merge, which is the recommended way.

Synchronization with master `sympy/sympy`
-----------------------------------------

Sometimes, you may need to merge your branch with the upstream master. Usually
you don't need to do this, but you may need to if

- Someone tells you that your branch needs to be merged because there are
  merge conflicts.

- sympy-bot tells you that your branch could not be merged.

- You need some change from master that was made after you started your branch.

Note, that after cloning a repository, it has a default remote called `origin`
that points to the `sympy/sympy` repository.  And your fork remote named as
`github`. You can observe the remotes names with the help of this command::

    $ git remote -v
    github  git@github.com:mynick/sympy.git (fetch)
    github  git@github.com:mynick/sympy.git (push)
    origin  git://github.com/sympy/sympy.git (fetch)
    origin  git://github.com/sympy/sympy.git (push)


As an example, consider that we have these commits in the master branch of local git repository::

    A---B---C        master

Then we have divergent branch `1234_sequences`::


    A---B---C           master
             \
              a---b     1234_sequences

In the meantime the remote `sympy/sympy` master repository was updated too::

    A---B---C---D       origin/master
    A---B---C           master
             \
              a---b     1234_sequences

There are basically two ways to get up to date with a changed master: rebasing
and merging.  Merging is recommended.

Merging creates a special commit, called a "merge commit", that joins your
branch and master together::

    A---B---C------D       origin/master
             \      \
              \      M     merge
               \    /
                a--b       1234_sequences


Note that the commits ``A``, ``B``, ``C``, and ``D`` from master and the
commits ``a`` and ``b`` from ``1234_sequences`` remain unchanged. Only the new
commit, ``M``, is added to ``1234_sequences``, which merges in the new commit
branch from master.

Rebasing essentially takes the commits from ``1234_sequences`` and reapplies
them on the latest master, so that it is as if you had made them from the
latest version of that branch instead.  Since these commits have a different
history, they are different (they will have different SHA1 hashes, and will
often have different content)::

    A---B---C---D---a'---b' origin/master

Rebasing is required if you want to edit your commit history (e.g., squash
commits, edit commit messages, remove unnecessary commits). But note that
since this rewrites history, it is possible to lose data this way, and it
makes it harder for people reviewing your code, because they can no longer
just look at the "new commits"; they have to look at everything again, because
all the commits are effectively new.

There are several advantages to merging instead of rebasing.  Rebasing
reapplies each commit iteratively over master, and if the state of the files
changed by that commit is different from when it was originally made, the
commit will change.  This means what you can end up getting commits that are
broken, or commits that do not do what they say they do (because the changes
have been "rebased out").  This can lead to confusion if someone in the future
tries to test something by checking out commits from the history.  Finally,
merge conflict resolutions can be more difficult with rebasing, because you
have to resolve the conflicts for each individual commit.  With merging, you
only have to resolve the conflicts between the branches, not the commits.  It
is quite common for a merge to not have any conflicts but for a rebase to have
several, because the conflicts are "already resolved" by later commits.

Merging keeps everything intact.  The commits you make are exactly the same,
down to the SHA1 hash, which means that if you checkout a commit from a merged
branch, it is exactly the same as checking it out from a non-merged branch.
What it does instead is create a single commit, the merge commit, that makes
it so that the history is both master and your branch.  This commit contains
all merge conflict resolution information, which is another advantage over
rebasing (all merge conflict resolutions when rebasing are "sifted" into the
commits that caused them, making them invisible).

Since this guide is aimed at new git users, you should be learning how to
merge.

Merging
~~~~~~~

First merge your local repository with the remote::

    $ git checkout master
    $ git pull

This results in::

    A---B---C---D       master
             \
              a---b     1234_sequences

Then merge your `1234_sequences` branch from `1234_sequences`::

    $ git checkout 1234_sequences
    $ git merge master

If the last command tells you that conflicts must be solved for a few indicated files.

If that's the case then the marks **>>>** and **<<<** will appear at those files. Fix the
code with **>>>** and **<<<** around it to what it should be.
You must manually remove useless pieces, and leave only new changes from your branch.

Then be sure that all tests pass::

    $ ./bin/test
    $ ./bin/doctest

and commit::

    $ git commit

So the result will be like that (automatic merging `c`)::

    A---B---C-------D     master
             \       \
              a---b---M   1234_sequences



Rebasing
~~~~~~~~

*Note*: merging is recommended over rebasing.

The final aim, that we want to obtain is::

    A---B---C---D           master
                 \
                  a---b     1234_sequences

The way to do it is first of all to merge local repository with the remote `sympy/sympy`::

    $ git checkout master
    $ git pull

So we obtain::

    A---B---C---D       master
             \
              a---b     1234_sequences

Then::

    $ git checkout 1234_sequences
    $ git rebase master

Note that this last one will require you to fix some merge conflicts if there are changes
to the same file in ``master`` and ``1234_sequences``. Open the file that it tells you is wrong,
fix the code with **>>>** and **<<<** around it to what it should be.

Then be sure that all tests pass::

    $ ./bin/test
    $ ./bin/doctest

Then do::

    $ git add sympy/matrices/your_conflict_file
    $ git rebase --continue

(git rebase will also guide you in this).

Changing of commit messages
~~~~~~~~~~~~~~~~~~~~~~~~~~~

The only time when it is recommended to rebase instead of merge is when you
need to edit your commit messages, or remove unnecessary commits.

Note, it is much better to get your commit messages right the first time.  See
the section on writing good commit messages above.

Consider these commit messages::

    $ git log --oneline
    7bbbc06 bugs fixing
    4d6137b some additional corrections.
    925d88fx sequences base implementation.


Then run *rebase* command in interactive mode::

    $ git rebase --interactive 925d88fx

Or you can use other ways to point to commits, e.g. *`git rebase --interactive HEAD^^`*
or *`git rebase --interactive HEAD~2`*.

A new editor window will appear (note that order is reversed with respect to the `git log` command)::

    pick 4d6137b some additional corrections.
    pick 7bbbc06 bugs fixing

    # Rebase 925d88f..7bbbc06 onto 925d88f
    #
    # Commands:
    #  p, pick = use commit
    #  r, reword = use commit, but edit the commit message
    #  e, edit = use commit, but stop for amending
    #  s, squash = use commit, but meld into previous commit
    #  f, fixup = like "squash", but discard this commit's log message

To edit a commit message, change *pick* to *reword* (or on old versions of
git, to *edit*) for those that you want to edit and save that file.

To squash two commits together, change *pick* to *squash*. To remove a commit,
just delete the line with the commit.

To edit a commit, change *pick* to *edit*.

After that, git will drop you back into your editor for every commit you want to reword,
and into the shell for every commit you wanted to edit::

    $ (Change the commit in any way you like.)
    $ git commit --amend -m "your new message"
    $ git rebase --continue

For commits that you want to edit, it will stop. You can then do::

    $ git reset --mixed HEAD^

This will "uncommit" all the changes from the commit. You can then recommit
them however you want. When you are done, remember to do::

    $ git rebase --continue

Most of this sequence will be explained to you by the output of the various commands of git.
Continue until it says: ::

    Successfully rebased and updated refs/heads/master.

If at any point you want to abort the rebase, do::

   $ git rebase --abort

**Warning**: this will run ``git reset --hard``, deleting any uncommitted
changes you have. If you want to save your uncommitted changes, run ``git
stash`` first, and then run ``git stash pop`` when you are done.

Reviewing patches
=================
Coding's only half the battle in software development: our code also has to be
thoroughly reviewed before release. Reviewers thus are an integral part of the
development process. Note that you do *not* have to have any special pull
or other privileges to review patches: anyone with Python on his/her computer
can review.

Pull requests (the preferred avenue for patches) for sympy are located
`here <https://github.com/sympy/sympy/pulls>`_. Feel free to view any open
pull request. Each contains a Discussion section for comments, Commits section
for viewing the author's commit files and documentation, and Diff section for
viewing all the changes in code. To browse the raw code files for a commit, select
a commit in the Commits section and click on the "View file" link to view a file.

Based on your level of expertise, there are two ways to participate in the
review process: manually running tests and using sympy-bot. Whichever option
you choose, you should also make sure that the committed code complies with
the [[Writing documentation]] guidelines.

In the Discussion section, you can add a comment at the end of the list, or you can click on individual lines of code and add a comment there.
Note that line comments tend to become invisible as amendments to the pull request change or remove the lines. The comments are not lost, just a mouse click away, but will not be readily visible anymore.

When discussing patches, be polite and stick to the point of the patch.
GitHub has published `an excellent set of guidelines for pull requests <https://github.com/blog/1943-how-to-write-the-perfect-pull-request>`_; it is recommended reading for reviewers as well as coders.

Manual testing
--------------
If you prefer to test code manually, you will first have to set up your
environment as described in the Workflow process section. Then, you need to
obtain the patched files. If you're reviewing a pull request, you should get
the requested branch into your sympy folder. Go into your folder and execute
(<username> being the username of the pull requester and <branchname> being
the git branch of the pull request)::

    $ git remote add <username> git://github.com/<username>/sympy.git
    $ git fetch <username>
    $ git checkout -b <branchname> <username>/<branchname>

After obtaining the pull request or patch, go to your sympy root directory and
execute::

    $ ./bin/test
    $ ./bin/doctest

If there are any problems, notify the author in the pull request by commenting.

Sympy-bot
---------
A good option for both new and veteran code reviewers is the sympy-bot program,
which automatically tests code in pull requests and posts the results in the
appropriate pull requests. To run sympy-bot, download the
`archive <https://github.com/sympy/sympy-bot/zipball/master>`_ and decompress it.
Go into the folder and execute::

    $ ./sympy-bot list

This will list all open pull requests on GitHub. To review a pull request and
run all tests, execute::

    $ ./sympy-bot review <pullrequest_number>

Alternatively, to review all open pull requests, execute::

    $ ./sympy-bot review all

The review command will post the results of all tested pull requests in the
appropriate Github page. For more information on sympy-bot, visit the
`readme <https://github.com/sympy/sympy-bot/blob/master/README.rst>`_.

Requirements for inclusion
--------------------------
A pull request or patch must meet the following requirements during review
before being considered as ready for release.

- All tests must pass.
    - Rationale: We need to make sure we're not releasing buggy code.
    - If new features are being implemented and/or bug fixes are added,
      tests should be added for them as well.
- The reviews (at least 1) must all be positive.
    - Rationale: We'd like everyone to agree on the merits of the patch.
    - If there are conflicting opinions, the reviewers should reach a consensus.
- The patch must have been posted for at least 24 hours.
    - Rationale: This gives a chance for everyone to look at the patch.


References
==========
.. .. rubric:: Footnotes
This page is based upon present SymPy_ pages [2-6], GitHub help [8-9], [11-12] and inspired
by Sage guide [10]:

.. [1] http://lkml.org/lkml/2000/8/25/132
.. [2] http://docs.sympy.org/dev/sympy-patches-tutorial.html#quick-start
.. [3] http://sympy.org/development.html
.. [4] https://github.com/sympy/sympy/wiki
.. [5] https://github.com/sympy/sympy/wiki/Pushing-patches
.. [6] https://github.com/sympy/sympy/wiki/Getting-the-bleeding-edge
.. [7] https://github.com/sympy/sympy/wiki/Git-hg-rosetta-stone
.. [8] http://help.github.com/pull-requests/
.. [9] http://help.github.com/fork-a-repo/
.. [10] http://sagemath.org/doc/developer/
.. [11] http://help.github.com/linux-set-up-git/
.. [12] http://help.github.com/troubleshooting-ssh/




.. _SymPy:          http://sympy.org/
.. _issues:         http://code.google.com/p/sympy/issues/list
.. _mailinglist:    sympy@googlegroups.com_
.. _sympy@googlegroups.com:             http://groups.google.com/group/sympy
.. _LICENSE:            https://github.com/sympy/sympy/blob/master/LICENSE
.. _`New BSD License`:    http://en.wikipedia.org/wiki/BSD_licenses#3-clause_license_.28.22New_BSD_License.22_or_.22Modified_BSD_License.22.29
.. _GitHub:             https://github.com/
.. _sympy/sympy:        https://github.com/sympy/sympy
.. _`#sympy at freenode`:                 irc://irc.freenode.net/sympy
