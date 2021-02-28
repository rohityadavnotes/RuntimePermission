package com.runtime.permission.utils;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.runtime.permission.R;

public class FragmentUtil {

    /**
     * Set fragments in frame layout.
     *
     * @param fragmentManager
     * @param fragment          fragment object.
     * @param fragmentTag       fragment tag in string
     * @param containerId       frame layout id.
     * @param canAddToBackStack if you true then add in old fragment add in back stack if false no old fragment add in back stack
     * @param withAnimation     if add open fragment with animation then true if without animation open fragment then false.
     */
    public static void setFragment(final FragmentManager fragmentManager,
                                   final Fragment fragment,
                                   final String fragmentTag,
                                   final @IdRes int containerId,
                                   final boolean canAddToBackStack,
                                   final boolean withAnimation,
                                   final boolean clearAllBackStack) {
        if (fragment != null) {
            if (clearAllBackStack) {
                fragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (withAnimation) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
            }

            fragmentTransaction.replace(containerId, fragment, fragmentTag);
            if (canAddToBackStack) {
                fragmentTransaction.addToBackStack(fragmentTag);
            }
            fragmentTransaction.commit();
        }
    }

    public static boolean isFragmentAdded(final Fragment fragment) {
        return fragment != null && fragment.isAdded();
    }

    public static Fragment getFragmentByTag(final FragmentManager fragmentManager, String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    public static Fragment getFragmentById(final FragmentManager fragmentManager, int id) {
        return fragmentManager.findFragmentById(id);
    }

    public static void removeFragment(final FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    /**
     * Close the number of fragments.
     *
     * @param fragmentManager
     * @param numBackStack,   number of fragments to pop up.
     */
    public static void popBackStack(final FragmentManager fragmentManager, int numBackStack) {
        int fragCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < fragCount - numBackStack; i++) {
            fragmentManager.popBackStack();
        }
    }

    /**
     * Close the all the fragment till the given tag name
     *
     * @param fragmentManager
     * @param tag             if tag name is null then all the fragment will be close or till the given tag name
     */
    public static void popBackStack(final FragmentManager fragmentManager, String tag) {
        fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment,
                                                 int frameId) {
        replaceFragmentInActivity(fragmentManager, fragment, frameId, null, null);
    }

    /**
     * 用replace向Activity中添加Fragment
     *
     * @param fragmentManager FragmentManger
     * @param fragment        Fragment
     * @param frameId         添加到View容器的id
     * @param tag             fragment tag
     * @param toBackStackStr  添加到返回堆栈
     */
    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment,
                                                 int frameId,
                                                 String tag,
                                                 String toBackStackStr) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (TextUtils.isEmpty(tag)) {
            transaction.replace(frameId, fragment);
        } else {
            transaction.replace(frameId, fragment, tag);
        }

        if (!TextUtils.isEmpty(toBackStackStr)) {
            transaction.addToBackStack(toBackStackStr);
        }
        transaction.commit();

    }

    /**
     * 用add将Fragment添加到Activity
     *
     * @param fragmentManager FragmentManger
     * @param fragment        Fragment
     * @param frameId         添加到View容器的id
     * @param tag             fragment tag
     */
    public static void addFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.commit();
    }

    /**
     * 创建Fragment的实例
     *
     * @param keys   设置参数时的key
     * @param values 对应key的值
     * @return 创建好的Fragment实例
     */
    public static <T extends Fragment> T setArgs(T fragment, String[] keys, String... values) {

        if (keys != null && values != null && keys.length != 0) {
            if (keys.length != values.length)
                throw new RuntimeException("keys size must be equal values size");

            Bundle args = new Bundle();

            for (int i = 0; i < keys.length; i++) {
                args.putString(keys[i], values[i]);
            }

            fragment.setArguments(args);
        }

        return fragment;

    }

    /**
     * Replace an existing fragment that was added to a container.
     *
     * @param fragmentManager
     * @param containerViewId
     * @param newFragment
     * @param bundle
     * @param canBack
     */
    public static void replaceFragment(@NonNull FragmentManager fragmentManager, int containerViewId, Fragment newFragment, Bundle bundle,
                                       boolean canBack) {
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.replace(containerViewId, newFragment, newFragment.getClass().getName());
        if (bundle != null) {
            newFragment.setArguments(bundle);
        }
        if (canBack) {
            mFragmentTransaction.addToBackStack(null);
        }
        mFragmentTransaction.commit();
    }

    /**
     * Add a fragment to the activity state. This fragment may optionally also have its view (if
     * {@link Fragment#onCreateView Fragment.onCreateView} returns non-null) into a container view of the activity.
     *
     * @param fragmentManager
     * @param containerViewId
     * @param newFragment
     * @param bundle
     * @param canBack
     */
    public static void addFragment(@NonNull FragmentManager fragmentManager, int containerViewId, Fragment newFragment, Bundle bundle,
                                   boolean canBack) {
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.add(containerViewId, newFragment, newFragment.getClass().getName());
        if (bundle != null) {
            newFragment.setArguments(bundle);
        }
        if (canBack) {
            mFragmentTransaction.addToBackStack(null);
        }
        mFragmentTransaction.commit();
    }

    /**
     * Hides an existing fragment. This is only relevant for fragments whose views have been added to a container, as
     * this will cause the view to be hidden.
     *
     * @param fragmentManager
     * @param containerViewId
     * @param previousFragment
     * @param newFragment
     * @param bundle
     * @param canBack
     */
    public static void hideFragment(@NonNull FragmentManager fragmentManager, int containerViewId, Fragment previousFragment,
                                    Fragment newFragment, Bundle bundle, boolean canBack) {
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        if (bundle != null) {
            newFragment.setArguments(bundle);
        }

        if (null != previousFragment) {
            mFragmentTransaction.hide(previousFragment);
        }
        mFragmentTransaction.add(containerViewId, newFragment, newFragment.getClass().getName());
        if (canBack && previousFragment != null) {
            mFragmentTransaction.addToBackStack(newFragment.getClass().getName());
        }
        mFragmentTransaction.commit();
    }

    /**
     * Remove an existing fragment. If it was added to a container, its view is also removed from that container.
     *
     * @param fragmentManager
     * @param names
     */
    public static void finishFragement(@NonNull FragmentManager fragmentManager, String... names) {
        int length = names.length;
        for (int i = 0; i < length; i++) {
            String name = names[i];
            fragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < length; i++) {
            String name = names[i];
            Fragment fragment = fragmentManager.findFragmentByTag(name);
            transaction.remove(fragment);
        }
        transaction.commit();
    }


    private FragmentUtil() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }
}
