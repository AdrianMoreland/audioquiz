//region USERPROFILE FRAGMENT
    /*



//        String username = StringExtensions.getText(binding.loginEmailEditText );
//        String password =  StringExtensions.getText(binding.loginPasswordEditText);
//        loginViewModel.login(username, password, LoginType.EMAIL);

    private void updateUiWithUser(LoggedInUserView user) {
        String welcome = getString(R.string.welcome) + user.getUsername();
        Log.d(TAG, "updateUiWithUser: " + welcome);

        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(requireContext(), welcome, Toast.LENGTH_LONG).show();
        }
    }




       loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), loginResult -> {
            if (loginResult instanceof LoginResult.Success) {
                onLoginSuccess();
            } else if (loginResult instanceof LoginResult.Error) {
                LoginResult.Error error = (LoginResult.Error) loginResult;
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    //   private final ViewBindingManager<FragmentLoginBinding> viewBindingManager = new ViewBindingManager<>(FragmentLoginBinding::inflate);
//    private final ToolbarComponentImpl toolbarComponent;
//    private final ToolbarConfiguration toolbarConfiguration;

    /*    public void onLoginSuccess() {
        if (loginFlowCoordinator != null) {
            sendCoordinatorEvent(new LoginCoordinatorEvent.OnLoginSuccess());
        }
    }

    protected <E extends CoordinatorEvent> void sendCoordinatorEvent(E event) {
        loginViewModel.sendCoordinatorEvent(event);
    }
*/

    /*  @Override
      public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
          super.onViewCreated(view, savedInstanceState);

          loginButton = binding.btnSignIn;
          loginGoogleButton = binding.btnLoginGoogle;
          loadingProgressBar = binding.loading;
           usernameEditText = binding.loginEmailEditText;
           passwordEditText = binding.loginPasswordEditText;
          isViewCreated = true;

          loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), loginFormState -> {
              if (loginFormState == null) { return;  }
              loginButton.setEnabled(loginFormState.isDataValid());
              if (loginFormState.getUsernameError() != null) {
                  usernameEditText.setError(getString(loginFormState.getUsernameError()));
              }
              if (loginFormState.getPasswordError() != null) {
                  passwordEditText.setError(getString(loginFormState.getPasswordError()));
              }
          });

          loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), loginResult -> {
              if (loginResult == null) {
                  Log.d(TAG, "onLoginResultChanged: loginResult is null");
                  return;
              }
              loadingProgressBar.setVisibility(View.GONE);
              if (loginResult instanceof LoginResult.Error) {
                  showLoginFailed(((LoginResult.Error) loginResult).getMessage());
                  Log.d(TAG, "onLoginResultChanged: loginResult is " + loginResult.getClass().getSimpleName());
              } else if (loginResult instanceof LoginResult.Success) {
                  Log.d(TAG, "onLoginResultChanged: loginResult is " + loginResult.getClass().getSimpleName());
                  updateUiWithUser(((LoginResult.Success) loginResult).getUser());
              }
          });

          setupLoginButton();
          setupGoogleButton();

          TextWatcher afterTextChangedListener = new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                  // ignore
              }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {
                  // ignore
              }

              @Override
              public void afterTextChanged(Editable s) {
                  loginViewModel.loginDataChanged(usernameEditText.getText().toString(), passwordEditText.getText().toString());
              }
          };
          usernameEditText.addTextChangedListener(afterTextChangedListener);
          passwordEditText.addTextChangedListener(afterTextChangedListener);
          passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
              if (actionId == EditorInfo.IME_ACTION_DONE) {
                  Log.d(TAG, "onEditorAction: IME_ACTION_DONE");
                  loginViewModel.login(usernameEditText.getText().toString(),
                          passwordEditText.getText().toString(),
                          LoginType.EMAIL);
              }
              return false;
          });

          binding.btnLoginToSignup.setOnClickListener(v -> onSignupClicked());
          binding.forgotPassword.setOnClickListener(v -> onForgotPasswordClicked());
          binding.btnBackLogin.setOnClickListener(v -> onBackClicked());

          // Observe LiveData
          loginViewModel.getPasswordResetResult().observe(getViewLifecycleOwner(), this::handlePasswordResetResult);
          loginViewModel.getPasswordResetMessage().observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
      }



      private void setupLoginButton() {
          loginButton.setOnClickListener(v -> {
              LoginFormState loginFormState = loginViewModel.getLoginFormState().getValue();
              if (loginFormState != null && loginFormState.isDataValid()) {
                  Log.d(TAG, "onClick: Form valid->  " + loginFormState.isDataValid());
                  loadingProgressBar.setVisibility(View.VISIBLE);
                  login(usernameEditText.getText().toString(),
                          passwordEditText.getText().toString(),
                          LoginType.EMAIL);
              } else {
                  Log.d(TAG, "onClick: Form not valid");
                  Toast.makeText(getContext(), "Please fill in the form correctly.", Toast.LENGTH_SHORT).show();
              }
          });

      }


      private void login(String idToken, String password, LoginType loginType) {
          loginViewModel.login(idToken, password, loginType);
      }



          private void onSignupClicked() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_signupFragment); // Replace with your action ID
    }

    private void onBackClicked() {
        NavHostFragment.findNavController(this).popBackStack();
    }

      binding.btnSignIn.setOnClickListener(v -> postAction(new LoginViewContract.Event.OnLoginButtonClicked()));

        binding.forgotPassword.setOnClickListener(v -> postAction(new LoginViewContract.Event.OnForgotPasswordLinkClicked()));

        binding.btnLoginToSignup.setOnClickListener(v -> postAction(new LoginViewContract.Event.OnSignupLinkClicked()));

        binding.loginPasswordEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                postAction(new LoginViewContract.Event.OnPasswordTextChanged(s.toString()));
            }
        });

        binding.loginEmailEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                postAction(new LoginViewContract.Event.OnUserNameTextChanged(s.toString()));
            }
        });


           @Override
        protected void onViewEffectReceived(LoginViewContract.Effect viewEvent) {
            if (viewEvent instanceof LoginViewContract.Effect.ShowErrorToast) {
                showToast(((LoginViewContract.Effect.ShowErrorToast) viewEvent).getError());
            }
        }

        @Override
        protected void onViewStateChanged(LoginViewContract.State viewState) {
            if (isViewCreated && getView() != null) {
                loadingProgressBar.setVisibility(viewState.isLoading() ? View.VISIBLE : View.GONE);
                binding.grpInput.setVisibility(viewState.isLoading() ? View.GONE : View.VISIBLE);
                loginButton.setEnabled(!viewState.isLoading());        }
        }*/
//endregion

//region LoginViewModel






    /*

        public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }


 public void loginDataChanged(String username, String password) {
        if (!checkUsernameErrors(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (checkPasswordErrors(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }
     public <E extends CoordinatorEvent> void sendCoordinatorEvent(E event) {
        executorService.submit(() -> coordinatorEvent.onNext(event));
    }

      public LiveData<LoginViewContract.State> getViewStates() {
        return viewState;
    }

    public LiveData<LoginViewContract.Event> getViewEvent() {
        return viewEvent;
    }
    //    private final ViewStateManager<LoginViewContract.State> viewStateManager = new ViewStateManager<>();
//    private final ViewEventManager<LoginViewContract.Event> viewEventManager = new ViewEventManager<>();
//    private final ViewEffectManager<LoginViewContract.Effect> viewEffectManager = new ViewEffectManager<>();
//    private final BehaviorSubject<CoordinatorEvent> coordinatorEvent = BehaviorSubject.create();
//    // State
//    private final MutableLiveData<LoginViewContract.State> viewState = new MutableLiveData<>();
//    // Events
//    private final MutableLiveData<LoginViewContract.Effect> viewEffect= new MutableLiveData<>();
//    // Actions
//    private final MutableLiveData<LoginViewContract.Event> viewEvent = new MutableLiveData<>();

    private LoginViewModel getViewModel() {
        return this;
    }

        @Override
    public void processAction(LoginViewContract.Event viewEvent) {

    }

      protected void updateViewState(java.util.function.Function<LoginViewContract.State, LoginViewContract.State> newState) {
        LoginViewContract.State updatedState = newState.apply(viewState.getValue());
        viewStateManager.updateViewState(updatedState);
    }

    public void updateViewEvent(LoginViewContract.Event event) {
        viewEventManager.sendViewEvent(event);
    }

    public void updateViewEffect(LoginViewContract.Effect effect) {
        viewEffectManager.sendViewEffect(effect);
    }


        protected void onViewStateChanged(ViewState viewState) {
        if (viewState instanceof LoginViewContract.State) {
            LoginViewContract.State loginState = (LoginViewContract.State) viewState;
           isLoading.postValue((loginState.isLoading()));
        }
    }

    protected void onViewEffectReceived(ViewEffect viewEffect) {
        if (viewEffect instanceof LoginViewContract.Effect.ShowErrorToast) {
           // showToast(((LoginViewContract.Effect.ShowErrorToast) viewEffect).getError());
        }
    }



    public void login(String username, String password, LoginType loginType) {
        isLoading.postValue(true);
        Log.d("LoginViewModel", "login: " + username + ", " + password + ", " + loginType);
        disposable.add(userAuthUseCase.login(username, password, loginType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.d("LoginViewModel", "Subscribe: " + AndroidSchedulers.mainThread().toString());
                    isLoading.postValue(false);
                    if (result instanceof Result.Success) {
                        Result.Success<?> successResult = (Result.Success<?>) result;
                        if (successResult.getData() instanceof LoggedInUser) {
                            LoggedInUser data = (LoggedInUser) successResult.getData();
                            LoggedInUserView loggedInUserView = new LoggedInUserView(System.currentTimeMillis(),
                                    data.getDisplayName(),
                                    data.getUserId(),
                                    data.getEmail());
                            Log.d("LoginViewModel", "Logged in user: " + loggedInUserView.getUsername() +data.getDisplayName());
                            Log.d("LoginViewModel", "Subscribe on thread " + Thread.currentThread().getName());
                            loginResult.postValue(new LoginResult(loggedInUserView));
                        } else {
                            loginResult.postValue(new LoginResult.Error("Unexpected data type"));
                        }
                    } else {
                        loginResult.postValue(new LoginResult.Error(errorMessage.getValue()));
                    }
                }, error -> {
                    isLoading.postValue(false);
                    handleLoginError(error);
                }));
    }*/

//endregion

//region googlesignindataasuuu



    /*
    public void firebaseAuthWithGoogle(String idToken) {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(true)
                .setNonce("<nonce string to use when generating a Google ID token>").build();

    }


    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList(CLIENT_ID))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build();

// (Receive idTokenString by HTTPS POST)

    GoogleIdToken idToken = verifier.verify(idTokenString);
if (idToken != null) {
        Payload payload = idToken.getPayload();

        // Print user identifier
        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);

        // Get profile information from payload
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        // Use or store profile information
        // ...

    } else {
        System.out.println("Invalid ID token.");
    }

*/
//endregion


//region BOTTOM SHEET FRAGMENT
/*
    private void applyChapterStyles(HomeViewContract.Chapter currentChapter) {
        // Convert dp to pixels
        final float scale = getResources().getDisplayMetrics().density;
        int widthLarge = (int) (44 * scale + 0.5f);
        int heightLarge = (int) (44 * scale + 0.5f);
        int widthSmall = (int) (25 * scale + 0.5f);
        int heightSmall = (int) (25 * scale + 0.5f);

        for (int i = 0; i < tvTitles.length; i++) {
            HomeViewContract.Chapter chapter = HomeViewContract.Chapter.values()[i];
            tvChapters[i].setTextColor(onSurfaceColor);
            tvTitles[i].setTextColor(onTertiaryColor);
            containerChapters[i].setBackgroundResource(0);
            if (chapter == currentChapter) {
                tvTitles[i].setTextColor(inversePrimaryColor);
                tvDescriptions[i].setTextColor(onTertiaryColor);
                tvDescriptions[i].setVisibility(View.VISIBLE);
                tvChapters[i].setTextColor(surfaceColor);
                containerChapters[i].setBackgroundResource(com.example.ui.R.drawable.shape_frame_rounded_chapter);
                quizButtons[i].setBackgroundTintList(ColorStateList.valueOf(primaryColor));
                quizButtons[i].setText("Start");

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) quizButtons[i].getLayoutParams();
                params.width = widthLarge;
                params.height = heightLarge;
                quizButtons[i].setLayoutParams(params);
            } else if (chapter.ordinal() < currentChapter.ordinal()) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) quizButtons[i].getLayoutParams();
                params.width = widthSmall;
                params.height = heightSmall;
                quizButtons[i].setLayoutParams(params);
                Drawable drawable = ContextCompat.getDrawable(requireContext(), com.example.ui.R.drawable.ic_correct);
                DrawableCompat.setTint(DrawableCompat.wrap(Objects.requireNonNull(drawable)), ContextCompat.getColor(requireContext(), com.example.ui.R.color.md_theme_surface));
                quizButtons[i].setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }
        }
    }


 private int getChapterArrayResId(String category) {
        switch (category.toLowerCase()) {
            case "soundwaves":
                return com.example.ui.R.array.soundwaves_chapters;
            case "synthesis":
                return com.example.ui.R.array.synthesis_chapters;
            case "production":
                return com.example.ui.R.array.production_chapters;
            case "mixing":
                return com.example.ui.R.array.mixing_chapters;
            case "processing":
                return com.example.ui.R.array.processing_chapters;
            case "musictheory":
                return com.example.ui.R.array.musictheory_chapters;
            default:
                return 0;
        }
    }
    private int getDescriptionArrayResId(String category) {
        switch (category.toLowerCase()) {
            case "soundwaves":
                return com.example.ui.R.array.soundwaves_descriptions;
            case "synthesis":
                return com.example.ui.R.array.synthesis_descriptions;
            case "production":
                return com.example.ui.R.array.production_descriptions;
            case "mixing":
                return com.example.ui.R.array.mixing_descriptions;
            case "processing":
                return com.example.ui.R.array.processing_descriptions;
            case "musictheory":
                return com.example.ui.R.array.musictheory_descriptions;
            default:
                return 0;
        }
    }


   tvTitles = new TextView[] {
        view.findViewById(R.id.tv_chapter_1),
                view.findViewById(R.id.tv_chapter_2),
                view.findViewById(R.id.tv_chapter_3),
                view.findViewById(R.id.tv_test),
                view.findViewById(R.id.tv_category_end_title)
    };
    tvChapters = new TextView[] {
        view.findViewById(R.id.tv_name_chapter_1),
                view.findViewById(R.id.tv_name_chapter_2),
                view.findViewById(R.id.tv_name_chapter_3),
    };
    tvDescriptions = new TextView[] {
        view.findViewById(R.id.tv_description_chapter_1),
                view.findViewById(R.id.tv_description_chapter_2),
                view.findViewById(R.id.tv_description_chapter_3),
                view.findViewById(R.id.tv_test_description),
                view.findViewById(R.id.tv_category_end_description)
    };
    containerChapters = new LinearLayout[] {
        view.findViewById(R.id.container_chapter_1),
                view.findViewById(R.id.container_chapter_2),
                view.findViewById(R.id.container_chapter_3),
                view.findViewById(R.id.container_test),
                view.findViewById(R.id.container_category_end)
    };
    quizButtons = new Button[] {
        view.findViewById(R.id.btn_chapter_1),
                view.findViewById(R.id.btn_chapter_2),
                view.findViewById(R.id.btn_chapter_3),
                view.findViewById(R.id.btn_test),
                view.findViewById(R.id.btn_finish_quiz)
    };*/
//        setupChapterData(category);
//        applyChapterStyles(currentChapter);
//        setupButtonListeners(category);
//
//        int primaryColor = com.example.ui.extensions.FragmentExtension.getColor(this, com.example.ui.R.color.md_theme_primary);
//        int inversePrimaryColor = com.example.ui.extensions.FragmentExtension.getColor(this, com.example.ui.R.color.md_theme_inversePrimary);
//        int surfaceColor = com.example.ui.extensions.FragmentExtension.getColor(this, com.example.ui.R.color.md_theme_surface);
//        int onTertiaryColor = com.example.ui.extensions.FragmentExtension.getColor(this, com.example.ui.R.color.md_theme_onTertiaryContainer);
//        int onSurfaceColor = com.example.ui.extensions.FragmentExtension.getColor(this, com.example.ui.R.color.md_theme_onSurface);


//        TextView tvCategoryTitle = view.findViewById(R.id.tv_category);
//        tvCategoryTitle.setText(category);
//
//        LinearProgressIndicator linearProgressIndicator = view.findViewById(R.id.progress_category);
//        final float progress = ((float) (currentChapter.ordinal() - 1) / 3) * 100;
//        linearProgressIndicator.setProgress((int) progress);

// Find views
//        TextView tvChapter1 = view.findViewById(R.id.tv_name_chapter_1);
//        TextView tvChapter2 = view.findViewById(R.id.tv_name_chapter_2);
//        TextView tvChapter3 = view.findViewById(R.id.tv_name_chapter_3);
//        TextView tvTest = view.findViewById(R.id.tv_test);
//        TextView tvFinishQuiz = view.findViewById(R.id.tv_category_end_title);
//        // Find views
//        TextView tvTitleChapter1 = view.findViewById(R.id.tv_chapter_1);
//        TextView tvTitleChapter2 = view.findViewById(R.id.tv_chapter_2);
//        TextView tvTitleChapter3 = view.findViewById(R.id.tv_chapter_3);
//
//        TextView tvDescription1 = view.findViewById(R.id.tv_description_chapter_1);
//        TextView tvDescription2 = view.findViewById(R.id.tv_description_chapter_2);
//        TextView tvDescription3 = view.findViewById(R.id.tv_description_chapter_3);
//        TextView tvDescriptionTest = view.findViewById(R.id.tv_test_description);
//        TextView tvDescriptionDone = view.findViewById(R.id.tv_category_end_description);
//
//        LinearLayout containerChapter1 = view.findViewById(R.id.container_chapter_1);
//        LinearLayout containerChapter2 = view.findViewById(R.id.container_chapter_2);
//        LinearLayout containerChapter3 = view.findViewById(R.id.container_chapter_3);
//        LinearLayout containerTest = view.findViewById(R.id.container_test);
//        LinearLayout containerDone = view.findViewById(R.id.container_category_end);
//
//        TextView tvAccuracy = view.findViewById(R.id.tv_accuracy);
//        TextView tvQuizzesTaken = view.findViewById(R.id.tv_quizzes_taken);
//        TextView tvLivesEarned = view.findViewById(R.id.tv_lives_earned);
//
//        // Set up button click listeners
//        Button quiz1Button = view.findViewById(R.id.btn_chapter_1);
//        Button quiz2Button = view.findViewById(R.id.btn_chapter_2);
//        Button quiz3Button = view.findViewById(R.id.btn_chapter_3);
//        Button quiz4Button = view.findViewById(R.id.btn_test);
//        Button quiz5Button = view.findViewById(R.id.btn_finish_quiz);
//
//        quiz1Button.setOnClickListener(v -> navigateToQuiz(category, 1));
//        quiz2Button.setOnClickListener(v -> navigateToQuiz(category, 2));
//        quiz3Button.setOnClickListener(v -> navigateToQuiz(category, 3));
//        quiz4Button.setOnClickListener(v -> navigateToQuiz(category, 4));
//        quiz5Button.setOnClickListener(v -> navigateToQuiz(category, 5));
//
//
//        // Set the chapter titles based on the category
//        int chapterArrayResId = getChapterArrayResId(Objects.requireNonNull(category));
//        if (chapterArrayResId != 0) {
//            String[] chapters = getResources().getStringArray(chapterArrayResId);
//            tvChapter1.setText(chapters[0]);
//            tvChapter2.setText(chapters[1]);
//            tvChapter3.setText(chapters[2]);
//            tvTest.setText("Final Exam");
//            tvFinishQuiz.setText("Done!");
//        }

// Set the descriptions based on the category
//        String[] descriptions = null;
//        int descriptionsArrayResId = getDescriptionArrayResId(Objects.requireNonNull(category));
//        if (descriptionsArrayResId != 0) {
//            descriptions = getResources().getStringArray(descriptionsArrayResId);
//            tvDescription1.setText(descriptions[0]);
//            tvDescription2.setText(descriptions[1]);
//            tvDescription3.setText(descriptions[2]);
//            tvDescriptionTest.setText(descriptions[3]);
//            tvDescriptionDone.setText(descriptions[4]);
//        }

//        tvDescription1.setText(null);
//        tvDescription2.setText(null);
//        tvDescription3.setText(null);
//        tvChapter1.setTextColor(onSurfaceColor);
//        tvChapter2.setTextColor(onSurfaceColor);
//        tvChapter3.setTextColor(onSurfaceColor);
//        tvTest.setTextColor(onSurfaceColor);
//        tvFinishQuiz.setTextColor(onSurfaceColor);
//        tvTitleChapter1.setTextColor(onTertiaryColor);
//        tvTitleChapter2.setTextColor(onTertiaryColor);
//        tvTitleChapter3.setTextColor(onTertiaryColor);
//        containerChapter1.setBackgroundResource(0);
//        containerChapter2.setBackgroundResource(0);
//        containerChapter3.setBackgroundResource(0);
//        containerTest.setBackgroundResource(0);
//        containerDone.setBackgroundResource(0);

// Store TextViews and LinearLayouts in arrays
//        TextView[] tvTitles = {tvTitleChapter1, tvTitleChapter2, tvTitleChapter3, tvTest, tvFinishQuiz};
//        TextView[] tvChapters = {tvChapter1, tvChapter2, tvChapter3, tvTest, tvFinishQuiz};
//        TextView[] tvDescriptions = {tvDescription1, tvDescription2, tvDescription3, tvDescriptionTest, tvDescriptionDone};
//        LinearLayout[] containerChapters = {containerChapter1, containerChapter2, containerChapter3, containerTest, containerDone};
//        Button[] quizButtons = {quiz1Button, quiz2Button, quiz3Button, quiz4Button, quiz5Button};
//
//        // Convert dp to pixels
//        final float scale = getResources().getDisplayMetrics().density;
//        int widthLarge = (int) (44 * scale + 0.5f);
//        int heightLarge = (int) (44 * scale + 0.5f);
//        int widthSmall = (int) (25 * scale + 0.5f);
//        int heightSmall = (int) (25 * scale + 0.5f);

//        for (int i = 0; i < quizButtons.length; i++) {
//            if (i + 1 == currentChapter.ordinal()) {
//                tvTitles[i].setTextColor(inversePrimaryColor);
//                if (descriptions != null) {
//                    tvDescriptions[i].setText(descriptions[i]);
//                }
//                tvDescriptions[i].setTextColor(onTertiaryColor);
//                tvDescriptions[i].setVisibility(View.VISIBLE);
//                if (i < tvChapters.length) {
//                    tvChapters[i].setTextColor(surfaceColor);
//                    containerChapters[i].setBackgroundResource(com.example.ui.R.drawable.shape_frame_rounded_chapter);
//                }
//                quizButtons[i].setBackgroundTintList(ColorStateList.valueOf(primaryColor));
//                quizButtons[i].setText("Start");
//
//                // Get the existing layout params and modify them
//                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) quizButtons[i].getLayoutParams();
//                params.width = widthLarge;
//                params.height = heightLarge;
//                quizButtons[i].setLayoutParams(params);
//
//            } else if (i < currentChapter.ordinal()) {
//                // Set properties for completed chapters
//                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) quizButtons[i].getLayoutParams();
//                params.width = widthSmall;
//                params.height = heightSmall;
//                quizButtons[i].setLayoutParams(params);
//                // Apply tint to the drawable
//                Drawable drawable = ContextCompat.getDrawable(requireContext(), com.example.ui.R.drawable.ic_correct);
//                DrawableCompat.setTint(DrawableCompat.wrap(Objects.requireNonNull(drawable)), ContextCompat.getColor(requireContext(), com.example.ui.R.color.md_theme_surface));
//                quizButtons[i].setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            } else {
//                if (i < tvChapters.length) {
//                    tvChapters[i].setTextColor(onSurfaceColor);
//                    containerChapters[i].setBackgroundResource(0);
//                }
//            }
//        }
//endregion